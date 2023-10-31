package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author han <handwasherhan@gmail.com>
 * Created on 2023
 */
public class FileReader {
    static Set<String> filenames = new HashSet<>();
    // key: word, value: filename-score set
    static Map<String, Map<String, Integer>> index = new HashMap<>();

    static void read() throws FileNotFoundException {
        for (int i = 0; i < 100; i++) {
            String pathname = "test" + i + ".txt";
            File file = new File(pathname);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                Scanner sc = new Scanner(fis);
                while (sc.hasNext()) {
                    String[] split = sc.nextLine().split(" ");
                    for (String str : split) {
                        if (!index.containsKey(str))
                            index.put(str, new HashMap<>());
                        Map<String, Integer> map = index.get(str);
                        if (map.containsKey(pathname)) {
                            map.put(pathname, map.get(pathname) + 1);
                        } else {
                            map.put(pathname, 1);
                        }
                    }
                }
            }
        }
    }

    static void search(String target) {
        if (!index.containsKey(target)) {
            System.out.println("无匹配项");
            return;
        }
        Map<String, Integer> map = index.get(target);
        List<Map.Entry<String, Integer>> collect = map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).toList();
        for (Map.Entry<String, Integer> entry : collect) {
            System.out.println(entry.getKey() + "得分：" + entry.getValue());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        read();
        Scanner scanner = new Scanner(System.in);
        for (String str = scanner.nextLine(); !"quit".equals(str); str = scanner.nextLine()) {
            search(str);
        }
    }
}
