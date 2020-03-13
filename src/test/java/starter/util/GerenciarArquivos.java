package starter.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.serenitybdd.core.pages.PageObject;

public class GerenciarArquivos extends PageObject{

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String path = Paths.get(System.getProperty("user.dir") + "/src/test/resources/English").toString();
		
		File folder = new File(path);
		listFilesForFolder(folder);
	}

	public static void listFilesForFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
				processar(fileEntry.toPath());
			}
		}
	}

	public static void processar(Path arquivoOrigem) throws IOException {
		List<String> linhas = Files.readAllLines(arquivoOrigem);
		List<String> linhasAlteradas = null;
		
		for(String linha : linhas) {
			String novoConteudo = null;
			if(linha.contains("<Table>")) {
				novoConteudo = "<Table>";
			}else if (linha.contains("</Table>")) {
				novoConteudo = "</Table>";
			}else if (linha.contains("<Row>")) {
				String[] valorInicio = linha.split("<Row><Cell>");
				String[] valorFinal = valorInicio[1].split("</Cell></Row>");
				String[] valor = valorFinal[2].split("</Cell><Cell>");
			}
			
			
			linhasAlteradas.add(0, novoConteudo);
		}

		

		Files.write(Paths.get("processado_" + arquivoOrigem), linhas);
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
