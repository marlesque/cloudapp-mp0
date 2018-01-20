import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP0 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP0(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
    List<String> sampleFile = Files.readAllLines(Paths.get(this.inputFileName));
        ArrayList<String> sample= new ArrayList<String>();
        for (int index: this.getIndexes()){
        	sample.add(sampleFile.get(index));
        }
        StringTokenizer ret1= new StringTokenizer(String.join("", sample), delimiter);
        ArrayList<String> ret2= new ArrayList<String>();
        ArrayList<String> ret3= new ArrayList<String>();
        while (ret1.hasMoreTokens()) {
        	String str= ret1.nextToken().toLowerCase();
        	int i=0;
        	Boolean containingAny= false;
        	while ((i < stopWordArray.length) && (!containingAny)) {
        		containingAny= str.contains(stopWordArray[i]) || containingAny;
        		i++;
        		}
        	if (!containingAny) {
        		ret2.add(str);
        	}
        	}
        List<Object> list = ret2.stream().distinct().collect(Collectors.toList());
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (Object s: list) {
        	int count = Collections.frequency(ret2, s);
        	map.put((String) s, count);
        }
        ArrayList<Integer> sortedValues= new ArrayList<Integer>(map.values());
        Collections.sort(sortedValues, Collections.reverseOrder());
        
       
        HashMap<Integer, String> map2= sortByValues(map);
        Set set2= map2.entrySet();
        Iterator iterator2= set2.iterator();
        int j= 0;
        while(iterator2.hasNext() && j < 20) {
            Map.Entry me2 = (Map.Entry)iterator2.next();
            ret[j]= (String) me2.getKey();
            j++;
       }
        return ret;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static HashMap sortByValues(HashMap map) { 
	List list = new LinkedList(map.entrySet());
	// Defined Custom Comparator here
	Collections.sort(list, new Comparator() {
	            public int compare(Object o2, Object o1) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });
	 HashMap sortedHashMap = new LinkedHashMap();
	 for (Iterator it = list.iterator(); it.hasNext();) {
	     Map.Entry entry = (Map.Entry) it.next();
	     sortedHashMap.put(entry.getKey(), entry.getValue());
	     } 
	  return sortedHashMap;
	  }
    

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP0 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP0 mp = new MP0(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
