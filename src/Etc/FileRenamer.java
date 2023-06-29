package Etc;
import java.io.File;
import java.util.Arrays;

public class FileRenamer {

    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\zaeli\\Downloads\\turkey_to_coffee\\turkey_to_coffee5"; // Replace with the actual path to your directory
        renameFilesNumerically(directoryPath);
    }

    private static void renameFilesNumerically(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        Arrays.sort(files, (a,b)-> {
        	String fa = ((File) a).getName();
        	int an =  Integer.parseInt(fa.substring(fa.lastIndexOf('-'), fa.indexOf('.')));

        	String fb = ((File) b).getName();
        	int bn =  Integer.parseInt(fb.substring(fb.lastIndexOf('-'), fb.indexOf('.')));
        	
        	if(an > bn) return -1;
        	else if(an == bn) return 0;
        	else return 1;
        });

        if (files != null) {
            int counter = 129;
            for (File file : files) {
                if (file.isFile()) {
                	System.out.println(file.getName());
                    String extension = getFileExtension(file);
                    String newName = String.format("%03d", counter) + extension;
                    File newFile = new File(directoryPath + File.separator + newName);
                    file.renameTo(newFile);
                    counter++;
                }
            }
            System.out.println("Files renamed successfully.");
        } else {
            System.out.println("Directory does not exist or is not accessible.");
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}

