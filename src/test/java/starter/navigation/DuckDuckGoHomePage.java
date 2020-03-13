package starter.navigation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://translate.google.com.br/?hl=pt-BR")
public class DuckDuckGoHomePage extends PageObject {
	
	public void traduzValor() {
		
		String path = Paths.get(System.getProperty("user.dir") + "/src/test/resources/English").toString();
		
		File folder = new File(path);
		listFilesForFolder(folder);
	}
	
	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
				processar(fileEntry.toPath());
			}
		}
	}

	public void processar(Path arquivoOrigem) {
		List<String> linhas = null;
		try {
			linhas = Files.readAllLines(arquivoOrigem);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> linhasAlteradas = new ArrayList<String>();
		
		int cont = 0;
		for(String linha : linhas) {
			
			if(cont == 258) {
				System.out.println(cont);
			}
			
			cont++;
			System.out.println(cont + " " + linha);
			
			String novoConteudo = "";
			
			if(linha.contains("<Table>")) {
				novoConteudo = "<Table>";
			}else if (linha.contains("</Table>")) {
				novoConteudo = "</Table>";
			}else if (linha.contains("<Row><Cell>") && linha.contains("</Cell></Row>")) {
				String[] valorFinal = linha.split("</Cell><Cell>");
				String traducao = null;
				if(valorFinal[2].split("</Cell></Row>").length>0) {
					String[] valor = valorFinal[2].split("</Cell></Row>");
					
					
					
					do {
						if(valor[0] == null) {
							traducao = valor[0];
						}else {
							traducao = traducao(valor[0]);
						}
						
					}while(traducao == null);
					 
					
					
				}else {
					novoConteudo = linha;
				}
				
				System.out.println(cont + " " + traducao);
				novoConteudo = valorFinal[0] + "</Cell><Cell>" +  valorFinal[1] + "</Cell><Cell>" + traducao + "</Cell></Row>";
				
			}else if (!linha.contains("<Row><Cell>") && !linha.contains("</Cell></Row>")) {
				String[] valorFinal = linha.split("</Cell><Cell>");
				String valor = valorFinal[1];
				
				String traducao = null;
				
				do {
					traducao = traducao(valor);
				}while(traducao == null);
				 
				
				System.out.println(cont + " " + traducao);
				novoConteudo = "</Cell><Cell>" + traducao;
			}else if (linha.contains("<Row><Cell>") && !linha.contains("</Cell></Row>")) {
				
				System.out.println(cont + " " + ">>> Pulando a linha");
				novoConteudo = linha;
			}else if (!linha.contains("<Row><Cell>") && !linha.contains("</Cell><Cell>")) {
				
				System.out.println(cont + " " + ">>> Pulando a linha");
				novoConteudo = linha;
			}else if (!linha.contains("<Row><Cell>") && linha.contains("</Cell></Row>")  && linha.contains("</Cell><Cell>")) {
				String[] valorFinal = linha.split("</Cell><Cell>");
				
				
				String[] valor = valorFinal[1].split("</Cell></Row>");
				
				String traducao = null;
				
				do {
					traducao = traducao(valor[0]);
				}while(traducao == null);
				 
				System.out.println(cont + " " + traducao);
				novoConteudo = "</Cell><Cell>" + traducao + "</Cell></Row>";
			}
			
			
			linhasAlteradas.add(novoConteudo);
		}

		try {
			Files.createFile(Paths.get(arquivoOrigem.getParent() + File.separator + "processado_" + arquivoOrigem.getFileName()));
			Files.write(Paths.get(arquivoOrigem.getParent() + File.separator + "processado_" + arquivoOrigem.getFileName()), linhasAlteradas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String traducao(String valor) {
		open();
		getDriver().findElement(By.id("source")).sendKeys(valor);
		getDriver().findElement(By.id("source")).sendKeys(Keys.ENTER);
		waitForAngularRequestsToFinish();
		String traducao = "";
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(getDriver().findElements(By.xpath("//*[@class='tlid-translation translation']")).size()>0){
			traducao = getDriver().findElement(By.xpath("//*[@class='tlid-translation translation']")).getText();
		}
//		
		return traducao;
		
	}
	
	public void alteraLinha(String palavraAntiga, String palavraNova) throws IOException {
	    String arquivo = "ARQUIVO";
	    String arquivoTmp = "ARQUIVO-tmp";

	    BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTmp));
	    BufferedReader reader = new BufferedReader(new FileReader(arquivo));

	    String linha;
	    while ((linha = reader.readLine()) != null) {
	        if (linha.contains(palavraAntiga)) {
	            linha = linha.replace(palavraAntiga, palavraNova);
	        }
	        writer.write(linha + "\n");
	    }

	    writer.close();        
	    reader.close();

	    
	}
	
	public void criaraquivo() {
		File f = new File("arquivo.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void acoes(String acao, String caminhoArquivo) {
		new File(caminhoArquivo).delete();
		new File(caminhoArquivo).renameTo(new File(caminhoArquivo));
	}
    
	
}
