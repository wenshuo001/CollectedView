package com.example.collectedview.ImageLoaderUtil;

import android.content.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

/**
 * Creator :Wen
 * DataTime: 2018/12/4
 * Description:
 */
public class DisLruCache {
    static final String JOURNAL = "journal";
    static final String JOURNAL_FILE = "journal,tmp";
    static final String MAGIC ="libcore.io.iskLruCache";
    static final String VERSION = "1";
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    private static final String REMOVE = "REMOVE";
    private static final String READ = "READ";

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private final File directory;
    private final File journalFile;
    private final File journalFileTmp;
    private final int appVersion;
    private final long maxSize;
    private final int valueCount;
    private long size = 0;
    private Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries
            = new LinkedHashMap<String, Entry>(0, 0.75f, true);
    private int redundantOpCount;




    private final class Entry {
        private final String key;

        /** Lengths of this entry's files. */
        private final long[] lengths;

        /** True if this entry has ever been published */
        private boolean readable;

        /** The ongoing edit or null if this entry is not being edited. */
        private SharedPreferences.Editor currentEditor;

        /** The sequence number of the most recently committed edit to this entry. */
        private long sequenceNumber;

        private Entry(String key) {
            this.key = key;
            this.lengths = new long[valueCount];
        }

        public String getLengths() throws IOException {
            StringBuilder result = new StringBuilder();
            for (long size : lengths) {
                result.append(' ').append(size);
            }
            return result.toString();
        }

        /**
         * Set lengths using decimal numbers like "10123".
         */
        private void setLengths(String[] strings) throws IOException {
            if (strings.length != valueCount) {
                throw invalidLengths(strings);
            }

            try {
                for (int i = 0; i < strings.length; i++) {
                    lengths[i] = Long.parseLong(strings[i]);
                }
            } catch (NumberFormatException e) {
                throw invalidLengths(strings);
            }
        }

        private IOException invalidLengths(String[] strings) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strings));
        }

        public File getCleanFile(int i) {
            return new File(directory, key + "." + i);
        }

        public File getDirtyFile(int i) {
            return new File(directory, key + "." + i + ".tmp");
        }
    }
}
