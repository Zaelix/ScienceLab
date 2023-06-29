package imageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveAsGif {

	public static void main(String[] args) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = jfc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (file.isFile() && (file.getName().contains(".jpg") || file.getName().contains(".png"))) {
				String input = JOptionPane.showInputDialog("How many images should it be split into?");
				int count = Integer.parseInt(input);
				BufferedImage[] images = cutImageIntoMultiple(file, count);

				String outputFilePath = JOptionPane.showInputDialog("Please name the output file:");
				try {
					saveImagesAsGif(images, outputFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				BufferedImage[] images = loadImagesFromDirectory(file);
				String outputFilePath = JOptionPane.showInputDialog("Please name the output file:");
				try {
					saveImagesAsGif(images, outputFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static BufferedImage[] cutImageIntoMultiple(File file, int columns) {
		BufferedImage[] images = new BufferedImage[columns];

		try {
			BufferedImage image = ImageIO.read(file);
			int width = image.getWidth() / columns;
			for (int i = 0; i < columns; i++) {
				images[i] = image.getSubimage(i*width, 0, width, image.getHeight());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finished cutting into " + columns + " images.");
		return images;

	}

	private static void saveImagesAsGif(BufferedImage[] images, String outputFilePath) throws IOException {
		// Create a GIF writer
		ImageWriter writer = ImageIO.getImageWritersByFormatName("gif").next();

		// Create an output file
		File outputFile = new File(outputFilePath);
		ImageOutputStream output = new FileImageOutputStream(outputFile);
		writer.setOutput(output);

		// Create a metadata object
		ImageWriteParam params = writer.getDefaultWriteParam();
		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier
				.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
		IIOMetadata metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

		// Configure the metadata for GIF
		String metaFormatName = metadata.getNativeMetadataFormatName();
		IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);
		IIOMetadataNode graphicsControlExtension = getNode(root, "GraphicControlExtension");
		graphicsControlExtension.setAttribute("disposalMethod", "none");
		graphicsControlExtension.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtension.setAttribute("transparentColorFlag", "FALSE");
		graphicsControlExtension.setAttribute("delayTime", "10"); // Modify delay time as needed
		graphicsControlExtension.setAttribute("transparentColorIndex", "0");
		IIOMetadataNode commentsExtension = getNode(root, "CommentExtensions");
		commentsExtension.setAttribute("CommentExtension", "Created by Java ImageIO");

		 // Set loop count for looping behavior
        IIOMetadataNode appExtensions = getNode(root, "ApplicationExtensions");
        IIOMetadataNode loopExtension = new IIOMetadataNode("ApplicationExtension");
        loopExtension.setAttribute("applicationID", "NETSCAPE");
        loopExtension.setAttribute("authenticationCode", "2.0");
        loopExtension.setUserObject(new byte[]{ 0x1, (byte) (0 & 0xFF), (byte) ((0 >> 8) & 0xFF) });
        appExtensions.appendChild(loopExtension);
        
		// Set the metadata
		metadata.setFromTree(metaFormatName, root);
		writer.prepareWriteSequence(metadata);
		int frame = 0;
		int frameCount = images.length;
		// Write each image as a frame in the GIF
		for (BufferedImage image : images) {
			// BufferedImage image = ImageIO.read(new File(imagePath));
			IIOImage iioImage = new IIOImage(image, null, metadata);
			writer.writeToSequence(iioImage, null);
			System.out.println("Inserted frame " + frame + " of "+ frameCount + " into gif.");
			frame++;
		}

		// Finish writing the GIF file
		writer.endWriteSequence();
		output.close();
		writer.dispose();
		System.out.println("Gif Complete.");
	}

	private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
				return (IIOMetadataNode) rootNode.item(i);
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return node;
	}

	private static BufferedImage[] loadImagesFromDirectory(File directory) {
		BufferedImage[] images = null;

		File[] files = directory.listFiles();
		if (files != null) {
			images = new BufferedImage[files.length];
			int i = 0;
			for (File f : files) {
				try {
					BufferedImage image = ImageIO.read(f);
					images[i] = image;
					i++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(f.getAbsolutePath());
			}
		}
		return images;
	}
}
