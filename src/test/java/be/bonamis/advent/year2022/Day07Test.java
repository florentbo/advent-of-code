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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
class Day07Test {

    private static final String CODE_TXT = "2022/07/2022_07_01_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/07/2022_07_input.txt");
        //log.info("Day07 part 01 result: {}", solvePart01(lines));
        //log.info("Day07 part 02 result: {}", solvePart02(lines));
    }

    @Test
        //@Disabled
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);

/*
        Tree<Integer> tree = node(1,
                node(2,
                        node(5), node(5)),
                node(6,
                        node(4), node(4)),
                node(2,
                        node(3), node(3))).asTree();

        Optional<Integer> firstIntegerGreaterThan4DepthFirst = tree.depthFirstSearch(val -> val > 4);
        Optional<Integer> firstIntegerGreaterThan4BreadthFirst = tree.breadthFirstSearch(val -> val > 4);
        //System.out.println(tree);
*/


        //Tree<Integer> tree2 = new Tree<>(1);

       /* assertTrue(firstIntegerGreaterThan4DepthFirst.isPresent());
        assertTrue(firstIntegerGreaterThan4BreadthFirst.isPresent());

        assertEquals((Integer) 5, firstIntegerGreaterThan4DepthFirst.get());
        assertEquals((Integer) 6, firstIntegerGreaterThan4BreadthFirst.get());*/


        Directory root = new Directory("/");
        TreeNode<Directory> rootNode = new TreeNode<>(root);
        Tree.Node<Directory> rootNode2 = node(root);
        Tree.Node<Directory> curNode2 = rootNode2;

        TreeNode<Directory> currentDirectory = rootNode;
        //Directory currentDirectory = root;
        for (int i = 1, linesSize = lines.size(); i < linesSize; i++) {
            String line = lines.get(i);
            if (isListFiles(line)) {
            } else if (isDirectory(line)) {

            } else if (isChangeDirectory(line)) {
                //System.out.println("change dir: " + line);
                String changedDirectoryName = getChangedDirectoryName(line);
                if (changedDirectoryName.equals("..")) {
                    Tree.Node<Directory> parentNode = curNode2.getParent().orElseThrow();
                    curNode2 = parentNode;
                } else {
                    Directory directory = new Directory(changedDirectoryName);
                    TreeNode<Directory> directoryNode = new TreeNode<>(directory);
                    Tree.Node<Directory> directoryNode2 = node(directory);

                    currentDirectory.addChild(directoryNode);
                    curNode2.addChildNode(directoryNode2);
                    //System.out.println(directory);
                    currentDirectory = directoryNode;
                    curNode2 = directoryNode2;
                }
            } else if (isFile(line)) {
                String[] fileDetails = line.split(" ");
                File file = new File(fileDetails[1], Long.parseLong(fileDetails[0]));
                curNode2.getData().addFile(file);
                //System.out.println(file);
                //currentDirectory.addFile(file);
            }

            //System.out.println(line);
        }

        /*
        $ cd /
$ ls
dir a
14848514 b.txt

         */

        System.out.println("before");
        System.out.println(rootNode2.asTree());
        System.out.println("after");

        System.out.println(isFile("14848514 b.txt"));
        System.out.println(isListFiles("$ ls"));
        System.out.println(isChangeDirectory("$ cd /"));
        System.out.println(isDirectory("dir a"));

       /* assertThatThrownBy(() -> validateStringFilenameUsingNIO2("b.txt"))
                .isInstanceOf(InvalidPathException.class)
                .hasMessageContaining("character not allowed");

*/
        System.out.println(getChangedDirectoryName("$ cd /"));
        System.out.println(getValue("dir (.*)", "dir a"));
        System.out.println(getFileName("14848514 b.txt"));
        //System.out.println(getValue("$ ls", "$ ls"));
        /*boolean find = matcher3.find();
        if (find) {
            String group = matcher3.group(1);
            System.out.println(group);
        }*/


        //assertThat(findSize(lines)).isEqualTo(95437);
    }

    private static String getChangedDirectoryName(String input) {
        return getValue(" cd (.*)", input);
    }

    private static String getFileName(String input) {
        return getValue("(.*) (.*)", input);
    }

    private boolean isDirectory(String input) {
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

    int findSize(List<String> lines) {
        Directory root = new Directory("/");
        TreeNode<Directory> rootNode = new TreeNode<>(root);

        /*List<Directory> directories = new ArrayList<>();
        directories.add(root);*/
        for (String line : lines) {
            String test = "$ cd /\n" +
                    "$ ls";

/*
            List<String> strings = Pattern.compile("\\$ cd (\\w)")
                    .matcher(line)
                    .results()
                    .map(MatchResult::group).toList();
            System.out.println(strings);
*/

        }
        return 0;
    }

    record Directory(String name, List<File> files) {

        public Directory(String name) {
            this(name, new ArrayList<>());
        }

        void addFile(File file) {
            this.files.add(file);
        }
    }

    record File(String name, long size) {

    }
}
