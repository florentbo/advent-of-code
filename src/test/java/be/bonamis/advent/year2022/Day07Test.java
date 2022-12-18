package be.bonamis.advent.year2022;

import com.github.rutledgepaulv.prune.Tree;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static com.github.rutledgepaulv.prune.Tree.node;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day07Test {

    private static final String CODE_TXT = "2022/07/2022_07_01_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/07/2022_07_input.txt");
        log.info("Day07 part 01 result: {}", solvePart01(lines));
        log.info("Day07 part 02 result: {}", solvePart02(lines));
    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(solvePart01(lines)).isEqualTo(95437);
    }

    private static Long solvePart01(List<String> lines) {
        List<Long> files = directorySizes(lines);
        System.out.println(files);

        return files.stream().filter(size -> size < 100000).reduce(0L, Long::sum);
    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(solvePart02(lines)).isEqualTo(24933642);
    }

    private static Long solvePart02(List<String> lines) {
        List<Long> files = directorySizes(lines);
        System.out.println(files);

        System.out.println(files.get(0));
        long spaceToCheck = files.get(0) - 40000000;
        System.out.println(spaceToCheck);

        return files.stream().filter(size -> size > spaceToCheck).min(Long::compareTo).orElseThrow();
    }

    private static List<Long> directorySizes(List<String> lines) {
        Directory root = new Directory("/");
        Tree.Node<Directory> directoryNode = node(root);
        Tree.Node<Directory> current = directoryNode;

        for (int i = 1, linesSize = lines.size(); i < linesSize; i++) {
            String line = lines.get(i);
            boolean isListOrDirectoryLine = isListFiles(line) || isDirectory(line);
            boolean isNotListOrDirectoryLine = !isListOrDirectoryLine;
            if (isNotListOrDirectoryLine && isChangeDirectory(line)) {
                String changedDirectoryName = getChangedDirectoryName(line);
                if (changedDirectoryName.equals("..")) {
                    current = current.getParent().orElseThrow();
                } else {
                    Directory directory = new Directory(changedDirectoryName);
                    Tree.Node<Directory> directoryNode2 = node(directory);
                    current.addChildNode(directoryNode2);
                    current = directoryNode2;
                }
            } else if (isNotListOrDirectoryLine && isFile(line)) {
                String[] fileDetails = line.split(" ");
                File file = new File(fileDetails[1], Long.parseLong(fileDetails[0]));
                current.getData().addFile(file);
            }
        }
        List<Long> files = new ArrayList<>();
        addTreeFilesSizes(directoryNode, files);
        return files;
    }

    private static Long treeFileSizes(Tree<Directory> directoryTree) {
        return directoryTree
                .depthFirstStreamNodes()
                .map(directoryNode1 -> directoryNode1.getData().filesSize())
                .reduce(0L, Long::sum);
    }

    private static void addTreeFilesSizes(Tree.Node<Directory> directoryNode, List<Long> files) {
        Long reduce = treeFileSizes(directoryNode.asTree());
        files.add(reduce);
        for (Tree.Node<Directory> child : directoryNode.getChildren()) {
            addTreeFilesSizes(child, files);
        }
    }

    private static String getChangedDirectoryName(String input) {
        return getValue(" cd (.*)", input);
    }

    private static String getFileName(String input) {
        return getValue("(.*) (.*)", input);
    }

    private static boolean isDirectory(String input) {
        return isMatches(input, "dir (.*)");
    }

    private static boolean isChangeDirectory(String input) {
        return isMatches(input, "[$] cd (.*)");
    }

    private static boolean isListFiles(String input) {
        return isMatches(input, "[$] ls");
    }

    private static boolean isFile(String input) {
        boolean matches = isMatches(input, "(.*) (.*)");
        if (matches) {
            return validateStringFilenameUsingNIO2(getFileName(input));
        }
        return false;
    }

    public static boolean validateStringFilenameUsingNIO2(String filename) {
        Paths.get(filename);
        return true;
    }

    private static boolean isMatches(String input, String regex) {
        return Pattern.compile(regex).matcher(input).matches();
    }

    private static String getValue(String regex, String input) {
        return Pattern.compile(regex).matcher(input)
                .results()
                .map(matchResult -> matchResult.group(1))
                .findFirst().orElseThrow();
    }


    record Directory(String name, List<File> files) {

        public Directory(String name) {
            this(name, new ArrayList<>());
        }

        void addFile(File file) {
            this.files.add(file);
        }

        long filesSize() {
            return this.files.stream().map(File::size).reduce(0L, Long::sum);
        }

        @Override
        public String toString() {
            return "Directory{" +
                    "name='" + name + '\'' +
                    ", files=" + files +
                    ", filesSize=" + filesSize() +
                    '}';
        }
    }

    record File(String name, long size) {

    }
}
