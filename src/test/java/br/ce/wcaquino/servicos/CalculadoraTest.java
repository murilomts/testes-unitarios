package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraTest {

	/**
	 * Cria-se primeiro o teste
	 * em seguida as classes e metodos necessarios
	 * 
	 */
	
	private Calculadora calc;
	
	@Test
	public void somarDoisValores() {
		int a = 5, b = 3;
		calc = new Calculadora();
		
		int resultado = calc.somar(a, b);
		
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void subtrairDoisValores() {
		int a = 8, b = 5;
		calc = new Calculadora();
		
		int resultado = calc.subtrair(a, b);
		
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void dividirDoisValores() throws NaoPodeDividirPorZeroException {
		int a = 6, b = 3;
		calc = new Calculadora();
		
		int resultado = calc.dividir(a, b);
		
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected=NaoPodeDividirPorZeroException.class)
	public void lancarExceptionAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 10, b = 0;
		calc = new Calculadora();
		
		calc.dividir(a, b);
		
	}
}
