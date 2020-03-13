package starter.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class listFilesForFolder {

	public File[] arquivos;
	
	public File[] getArquivos() {
		return arquivos;
	}

	public void setArquivos(File[] arquivos) {
		this.arquivos = arquivos;
	}

	public void listFilesForFolder() {
	}
	public void listFilesForFolder(File folder) {
		openfiles(folder);
	}

	public void openfiles(File folder) {
		arquivos = folder.listFiles();
	}
	
	public int numeroOcorrencias(File arquivo) {
		
		int contagem = 0;
		List<String> linhas = null;
		try {
			linhas = Files.readAllLines(arquivo.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String linha : linhas) {
			if(linha.contains("<Row><Cell>")) {
				contagem++;
			}
		}
		return contagem;
	}
}
