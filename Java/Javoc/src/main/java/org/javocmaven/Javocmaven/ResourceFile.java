package org.javocmaven.Javocmaven;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ResourceFile {
	public static void main(final String[] args)
			throws URISyntaxException, ZipException, IOException, InterruptedException {
		URI zipfile = getFile(getJarURI(), "clumsy-0.2-win64.zip");
		String zipfilepath = new File(zipfile).getPath();
		String folder = getJarDir();
		extractFolder(zipfilepath, folder);
		Thread.sleep(5000);
		deleteResource(zipfilepath, "file");
		deleteResource(folder, "dir");
		
	}

	protected static String getJarDir() throws URISyntaxException {
		return new File(ResourceFile.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
	}

	protected static URI getJarURI() throws URISyntaxException {
		return ResourceFile.class.getProtectionDomain().getCodeSource().getLocation().toURI();
	}

	protected static URI getFile(final URI where, final String fileName) throws ZipException, IOException {
		File location;
		URI fileURI;

		location = new File(where);

		// not in a JAR, just return the path on disk
		if (location.isDirectory()) {
			fileURI = URI.create(where.toString() + fileName);
		} else {
			ZipFile zipFile;

			zipFile = new ZipFile(location);

			try {
				fileURI = extractFile(zipFile, fileName);
			} finally {
				zipFile.close();
			}
		}
		return (fileURI);
	}

	protected static URI extractFile(final ZipFile zipFile, final String fileName) throws IOException {
		File tempFile;
		ZipEntry entry;
		InputStream zipStream;
		OutputStream fileStream;

//		tempFile = File.createTempFile(fileName, Long.toString(System.currentTimeMillis()));
//		tempFile = File.createTempFile(fileName, ".zip");
		tempFile = new File(fileName);
//		tempFile.deleteOnExit();
		entry = zipFile.getEntry(fileName);

		if (entry == null) {
			throw new FileNotFoundException("cannot find file: " + fileName + " in archive: " + zipFile.getName());
		}

		zipStream = zipFile.getInputStream(entry);
		fileStream = null;

		try {
			byte[] buf;
			int i;

			fileStream = new FileOutputStream(tempFile);
			buf = new byte[1024];
			i = 0;

			while ((i = zipStream.read(buf)) != -1) {
				fileStream.write(buf, 0, i);
			}
		} finally {
			close(zipStream);
			close(fileStream);
		}
		return (tempFile.toURI());
	}

	protected static void extractFolder(String source, String dest) throws IOException {
		File destDir = new File(dest);
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(source));		
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			File newFile = newFile(destDir, zipEntry);
			if (zipEntry.isDirectory()) {
				if (!newFile.isDirectory() && !newFile.mkdirs()) {
					throw new IOException("Failed to create directory " + newFile);
				}
			} else {
				// fix for Windows-created archives
				File parent = newFile.getParentFile();
				if (!parent.isDirectory() && !parent.mkdirs()) {
					throw new IOException("Failed to create directory " + parent);
				}

				// write file content
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}

	protected static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	protected static void close(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	protected static boolean deleteResource(String path, String type) throws InputMismatchException {
		File resource = new File(path);
		if (type.equals("file")) {
			if (!resource.exists()) {
				throw new InputMismatchException("Specified file, but path does not point to a file.");
			} else {
				return resource.delete();
			}
		} else if (type.equals("dir")) {
			if (!resource.isDirectory()) {
				throw new InputMismatchException("Specified directory, but path does not point to a directory.");
			} else {
				return deleteDirectory(resource);
			}

		} else {
			throw new InputMismatchException("Type not specified correctly, either 'file' or 'dir' can be used.");
		}
	}

	protected static boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}
}
