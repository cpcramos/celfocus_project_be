/**
 * 
 */
package celfocus.project.be.java.files;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * The purpose of FileHandler is to handle file operations like read, write or print
 *
 * @author cpcramos
 */
public class FileHandler {
	private String filename = "";
	private Stream<String> listFileLines = null;

	/**
	 * FileHandler constructor
	 * @param filename
	 * @throws IOException
	 */
	public FileHandler(String filename) throws IOException{
		this.filename = filename;
		this.listFileLines = readFileLines();
	}
	
	/**
	 * readFileLines: Reads a txt file and saves it in a Stream of Strings
	 * @return Stream<String>
	 * @throws IOException
	 */
	private Stream<String> readFileLines() throws IOException {
		return Files.lines(Path.of(this.filename));
	}
	
	/**
	 * writeFileLines: generates an output file as txt
	 * @param outputFilename
	 * @param outputFileContent
	 * @throws IOException
	 */
	private void writeFileLines(String outputFilename, List<String> outputFileContent) throws IOException {
		FileWriter writer = new FileWriter(outputFilename); 
		for(String str: outputFileContent) {
		  writer.write(str + System.lineSeparator());
		}
		writer.close();
		
		
	}
	
	/**
	 * testWriteFileLines: tests the method writeFileLines
	 * @param filename
	 * @param listFileLines
	 * @throws IOException
	 */
	private void testWriteFileLines(String filename, List<String> listFileLines) throws IOException {
		System.out.println("***** testWriteFileLines*****");
		writeFileLines("output.txt", listFileLines);
	}
	
	/**
	 * getFilename: filename getter
	 * @return
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * setFilename: filename setter
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * getListFileLines: listFileLines getter
	 * @return the listFileLines
	 */
	public Stream<String> getListFileLines() {
		return listFileLines;
	}

	/**
	 * setListFileLines: listFileLines setter
	 * @param fileContentLines the fileContentLines to set
	 */
	public void setListFileLines(Stream<String> listFileLines) {
		this.listFileLines = listFileLines;
	}
	
	/**
	 * printFile: print file content to the console
	 * @param filename
	 */
	public void printFile(String filename) {
		try (Stream<String> lines = new FileHandler(filename).getListFileLines()) {
            lines.forEach(s -> System.out.println(s));     
		}catch (IOException ioEx){ 
		System.out.println(ioEx);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileHandler fileHandler = new FileHandler("coutryCodes.txt");
			fileHandler.printFile("coutryCodes.txt");
			fileHandler.testWriteFileLines("coutryCodesOutput.txt", fileHandler.getListFileLines().toList());
		}catch(IOException ioEx) {
			System.out.println(ioEx);
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		
	}
}
