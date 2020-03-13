package starter.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GerarArquivosMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		H2jdbc banco = new H2jdbc();
		String fileName = "text_ui_dialog.xml";
		String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\English" + File.separator;
		
		//cria tabela e inserir dados
		String[][] resultados = banco.lerArquivo(fileName);
		
		ArrayList<String> linhasAlteradas = new ArrayList<String>();
		String novoConteudo = "";
		
		linhasAlteradas.add("<Table>");
		for (String[] resultado : resultados) {
			novoConteudo = "<Row><Cell>"+resultado[1]+"</Cell><Cell>"+resultado[2]+"</Cell><Cell>"+resultado[3]+"</Cell></Row>";
			linhasAlteradas.add(novoConteudo);
		}
		linhasAlteradas.add("</Table>");
		
		try {
			Files.createFile(Paths.get(filePath + "processado_" + fileName));
			Files.write(Paths.get(filePath + "processado_" + fileName), linhasAlteradas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
