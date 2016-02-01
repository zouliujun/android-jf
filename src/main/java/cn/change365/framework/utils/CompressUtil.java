package cn.change365.framework.utils;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Jack on 2015/8/17.
 */
public class CompressUtil {

//    /** Gzip the contents of the from file and save in the to file. */
//    public static void gzipFile(String from, String to) throws IOException {
//        // Create stream to read from the from file
//        FileInputStream in = new FileInputStream(from);
//        // Create stream to compress data and write it to the to file.
//        GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(to));
//        // Copy bytes from one stream to the other
//        byte[] buffer = new byte[4096];
//        int bytes_read;
//        while ((bytes_read = in.read(buffer)) != -1)
//            out.write(buffer, 0, bytes_read);
//        // And close the streams
//        in.close();
//        out.close();
//    }

    public static void createTar(String[] inputFiles, String output) throws Exception {
        ArchiveOutputStream aos = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR, new FileOutputStream(output));
        for(String inputFile : inputFiles){
            TarArchiveEntry entry = new TarArchiveEntry(inputFile);
            aos.putArchiveEntry(entry);
            IOUtils.copy(new FileInputStream(inputFile), aos);
            aos.closeArchiveEntry();
        }
        aos.close();
    }

    public static void createGzFile(String inputFile, String outPutFile) throws Exception {
        CompressorOutputStream cos = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, new FileOutputStream(outPutFile));
        IOUtils.copy(new FileInputStream(inputFile), cos);
        cos.close();
    }


//    /** Zip the contents of the directory, and save it in the zipfile */
//    public static void zipDirectory(String dir, String zipfile) throws IOException,
//            IllegalArgumentException {
//        // Check that the directory is a directory, and get its contents
//        File d = new File(dir);
//        if (!d.isDirectory())
//            throw new IllegalArgumentException("Compress: not a directory:  " + dir);
//        String[] entries = d.list();
//        byte[] buffer = new byte[4096]; // Create a buffer for copying
//        int bytes_read;
//
//        // Create a stream to compress data and write it to the zipfile
//        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
//
//        // Loop through all entries in the directory
//        for (int i = 0; i < entries.length; i++) {
//            File f = new File(d, entries[i]);
//            if (f.isDirectory())
//                continue; // Don't zip sub-directories
//            FileInputStream in = new FileInputStream(f); // Stream to read file
//            ZipEntry entry = new ZipEntry(f.getPath()); // Make a ZipEntry
//            out.putNextEntry(entry); // Store entry
//            while ((bytes_read = in.read(buffer)) != -1)
//                // Copy bytes
//                out.write(buffer, 0, bytes_read);
//            in.close(); // Close input stream
//        }
//        // When we're done with the whole loop, close the output stream
//        out.close();
//    }
}
