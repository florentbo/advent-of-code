package be.bonamis.advent.year2022;

import com.github.rutledgepaulv.prune.Tree;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static com.github.rutledgepaulv.prune.Tree.node;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day07Test {

    private static final String CODE_TXT = "2022/07/2022_07_01_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/07/2022_07_input.txt");
        log.info("Day07 part 01 result: {}", solvePart01(lines));
        //log.info("Day07 part 02 result: {}", solvePart02(lines));
    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(solvePart01(lines)).isEqualTo(95437);


        /*

        //System.out.println(directoryNode.getData().name);

        int size = directoryNode.getChildren().size();
        directoryTree.depthFirstStreamNodes().forEach(new Consumer<Tree.Node<Directory>>() {
            @Override
            public void accept(Tree.Node<Directory> directoryNode) {
                int size = directoryNode.getChildren().size();
                String name = directoryNode.getData().name;
                //System.out.println(size);
                //System.out.println(name);
                //log.info("name: {} size {}", name, size);
            }
        });

        Long reduce = treeFileSizes(directoryTree);
        System.out.println("size: " + reduce);


        System.out.println(isFile("14848514 b.txt"));
        System.out.println(isListFiles("$ ls"));
        System.out.println(isChangeDirectory("$ cd /"));
        System.out.println(isDirectory("dir a"));

       *//* assertThatThrownBy(() -> validateStringFilenameUsingNIO2("b.txt"))
                .isInstanceOf(InvalidPathException.class)
                .hasMessageContaining("character not allowed");

*//*
        System.out.println(getChangedDirectoryName("$ cd /"));
        System.out.println(getValue("dir (.*)", "dir a"));
        System.out.println(getFileName("14848514 b.txt"));
        //System.out.println(getValue("$ ls", "$ ls"));
        *//*boolean find = matcher3.find();
        if (find) {
            String group = matcher3.group(1);
            System.out.println(group);
        }*//*
*/

        //assertThat(findSize(lines)).isEqualTo(95437);
    }

    private static Long solvePart01(List<String> lines) {
        Directory root = new Directory("/");
        Tree.Node<Directory> directoryNode = node(root);
        Tree.Node<Directory> current = directoryNode;

        for (int i = 1, linesSize = lines.size(); i < linesSize; i++) {
            String line = lines.get(i);
            if (isListFiles(line)) {
            } else if (isDirectory(line)) {

            } else if (isChangeDirectory(line)) {
                //System.out.println("change dir: " + line);
                String changedDirectoryName = getChangedDirectoryName(line);
                if (changedDirectoryName.equals("..")) {
                    current = current.getParent().orElseThrow();
                } else {
                    Directory directory = new Directory(changedDirectoryName);
                    //TreeNode<Directory> directoryNode = new TreeNode<>(directory);
                    Tree.Node<Directory> directoryNode2 = node(directory);

                    //currentDirectory.addChild(directoryNode);
                    current.addChildNode(directoryNode2);
                    //System.out.println(directory);
                    //currentDirectory = directoryNode;
                    current = directoryNode2;
                }
            } else if (isFile(line)) {
                String[] fileDetails = line.split(" ");
                File file = new File(fileDetails[1], Long.parseLong(fileDetails[0]));
                current.getData().addFile(file);
            }

        }

        Tree<Directory> directoryTree = directoryNode.asTree();


        System.out.println(directoryTree);
        List<String> names = new ArrayList<>();
        List<Long> files = new ArrayList<>();
        printPostorder(directoryNode, names, files);
        System.out.println(names);
        System.out.println(files);
        return files.stream().filter(size -> size < 100000).reduce(0L, Long::sum);
    }

    private static Long treeFileSizes(Tree<Directory> directoryTree) {
        return directoryTree
                .depthFirstStreamNodes()
                .map(directoryNode1 -> directoryNode1.getData().filesSize())
                .reduce(0L, Long::sum);
    }

    private static void printPostorder(Tree.Node<Directory> directoryNode, List<String> names, List<Long> files) {
        String name = directoryNode.getData().name;
        log.info("name: {}", name);
        names.add(name);
        Long reduce = treeFileSizes(directoryNode.asTree());
        files.add(reduce);
        for (Tree.Node<Directory> child : directoryNode.getChildren()) {
            printPostorder(child, names, files);
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

    @Test
    @Disabled
    void solvePart02() {
        assertThat(new Day07(getLines(CODE_TXT)).solvePart02()).isEqualTo(24);
    }

    record Directory(String name, List<File> files) {

        public Directory(String name) {
            this(name, new ArrayList<>());
        }

        void addFile(File file) {
            this.files.add(file);
        }

        long filesSize(){
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
