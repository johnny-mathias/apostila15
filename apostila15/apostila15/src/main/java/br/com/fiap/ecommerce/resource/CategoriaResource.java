package br.com.fiap.ecommerce.resource;

import br.com.fiap.ecommerce.dao.CategoriaDao;
import br.com.fiap.ecommerce.dto.categoria.AtualizarCategoriaDto;
import br.com.fiap.ecommerce.dto.categoria.CadastroCategoriaDto;
import br.com.fiap.ecommerce.dto.categoria.DetalhesCategoriaDto;
import br.com.fiap.ecommerce.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecommerce.model.Categoria;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/categorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    private CategoriaDao categoriaDao;

    @Inject
    private ModelMapper modelMapper;

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int codigo) throws EntidadeNaoEncontradaException, SQLException {
        categoriaDao.deletar(codigo);
        return Response.noContent().build(); // 204 No content
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int codigo, AtualizarCategoriaDto dto) throws EntidadeNaoEncontradaException, SQLException {
        Categoria categoria = modelMapper.map(dto, Categoria.class);
        categoria.setCodigo(codigo);
        categoriaDao.atualizar(categoria);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws SQLException, EntidadeNaoEncontradaException {
        DetalhesCategoriaDto dto = modelMapper
                .map(categoriaDao.buscar(codigo), DetalhesCategoriaDto.class);
        return Response.ok(dto).build();
    }

    @GET
    public List<DetalhesCategoriaDto> listar() throws SQLException {
        return categoriaDao.listar().stream().map(
                p -> modelMapper.map(p, DetalhesCategoriaDto.class)
        ).toList();
    }

    @POST
    public Response create(CadastroCategoriaDto dto, @Context UriInfo uriInfo) throws SQLException {
        Categoria categoria = modelMapper.map(dto, Categoria.class);

        categoriaDao.cadastrar(categoria);

        //Constroi uma URL de retorno, para acessar o recurso criado
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(categoria.getCodigo())).build();

        return Response.created(uri)
                .entity(modelMapper.map(categoria, DetalhesCategoriaDto.class))
                .build(); //HTTP STATUS CODE 201 (Created)
    }

}
