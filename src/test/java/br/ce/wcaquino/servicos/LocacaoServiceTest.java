package br.ce.wcaquino.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void teste() throws Exception {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao;
			locacao = service.alugarFilme(usuario, filme);
			
			//verificacao
			error.checkThat(locacao.getValor(), is(equalTo(5.0)));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque_elegante() throws Exception{
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);
		
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testeLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 1, 5.0);
		
			try {
				service.alugarFilme(null, filme);
			} catch (LocadoraException e) {
				assertThat(e.getMessage(), is("Usuario vazio."));
			}
	}

	@Test
	public void testeLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio.");
		
		service.alugarFilme(usuario, null);
	}
	
	@Test()
	public void testLocacao_filmeSemEstoque_robusto(){
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		try {
			service.alugarFilme(usuario, filme);
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	
	@Test()
	public void testLocacao_filmeSemEstoque_new() throws Exception{
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 4.0);
		
		exception.expect(Exception.class);
			
		service.alugarFilme(usuario, filme);
		
	}

}
