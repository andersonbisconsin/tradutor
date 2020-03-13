package starter.util;

import java.io.File;
import java.nio.file.Paths;

public class LerArquivosMain {

	public static void main(String[] args) {
		//Processar todos os arquivos
		processar();
	}
	
	public static void processar() {
		// TODO Auto-generated method stub
		String path = Paths.get(System.getProperty("user.dir") + "/src/test/resources/English").toString();
		
		File folder = new File(path);
		listFilesForFolder arquivos = new listFilesForFolder();
		ReadAndPrintXMLFile carregaXml = new ReadAndPrintXMLFile();
		H2jdbc banco = new H2jdbc();
		
		
		//cria tabela e inserir dados
		banco.createTable();
		arquivos.openfiles(folder);
		
		for (final File fileEntry : arquivos.arquivos) {
			System.out.println(fileEntry.getName());
			banco.inserir(carregaXml.carregaXML(fileEntry, arquivos.numeroOcorrencias(fileEntry),5));
		}
		
	}
}
