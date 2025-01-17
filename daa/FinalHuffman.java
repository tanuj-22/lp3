import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Hnode{

    Hnode left = null,right = null;
    int freq = 0;
    char _character = '0';

    Hnode(Hnode left,Hnode right,char _character,int freq){

        this.left = left;
        this.right = right;
        this._character = _character;
        this.freq = freq;

    }

}


public class FinalHuffman {

    Hnode tree = null;
    HashMap<Character,String> codes = new HashMap<>();
    public void printCode(Hnode root, String s){



        if(root.left == null && root.right == null ){
            System.out.println(root._character+"   -->  "+s);
            codes.put(root._character,s);
            return;
        }

        printCode(root.left,s+"0");
        printCode(root.right, s + "1");


    }


    public void solve(HashMap<Character,Integer> hmap){

        PriorityQueue<Hnode> pq = new PriorityQueue<>((a,b) -> a.freq - b.freq);
        for(Map.Entry<Character,Integer> x : hmap.entrySet()){
            Hnode temp = new Hnode(null,null,x.getKey(),x.getValue());
            pq.add(temp);
        }

        while (pq.size() > 1){

            Hnode first = pq.poll();
            Hnode second = pq.poll();

            Hnode internal = new Hnode(first,second,'.', first.freq+second.freq);
            pq.add(internal);



        }

        Hnode root = pq.peek();
        tree = root;
        printCode(root,"");


    }




    public String encryptMsg(char[] str){

        StringBuilder s = new StringBuilder();
        for(char x : str){
            s.append(codes.get(x));
        }

        return s.toString();


    }

    public String decryptMsg(String cipher){

        int index = 0;
        StringBuilder s = new StringBuilder();
        Hnode curr = tree;
        while(index < cipher.length()){

            if(cipher.charAt(index) == '0') curr = curr.left;
            else curr = curr.right;

            if(curr.left == null && curr.right == null){
                s.append(curr._character);
                curr = tree;
            }
            index++;


        }


        return s.toString();


    }


    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Enter The String : ");

        char[] str = br.readLine().trim().toCharArray();
        HashMap<Character,Integer> hmap = new HashMap<>();
        for(Character c : str){
            hmap.put(c,hmap.getOrDefault(c,0)+1);
        }


        FinalHuffman obj1 = new FinalHuffman();
        obj1.solve(hmap);

        String ciperText = obj1.encryptMsg(str);
        System.out.println("ciperText : " + ciperText);
        String msg = obj1.decryptMsg(ciperText);
        System.out.println("MSG : " + msg);

    }
}





//    Huffman coding is a lossless data compression algorithm. The idea is to assign variable-length codes to input characters, lengths of the assigned codes are based on the frequencies of corresponding characters. The most frequent character gets the smallest code and the least frequent character gets the largest code.
//        The variable-length codes assigned to input characters are Prefix Codes, means the codes (bit sequences) are assigned in such a way that the code assigned to one character is not the prefix of code assigned to any other character. This is how Huffman Coding makes sure that there is no ambiguity when decoding the generated bitstream.
//        Let us understand prefix codes with a counter example. Let there be four characters a, b, c and d, and their corresponding variable length codes be 00, 01, 0 and 1. This coding leads to ambiguity because code assigned to c is the prefix of codes assigned to a and b. If the compressed bit stream is 0001, the de-compressed output may be “cccd” or “ccb” or “acd” or “ab”.
//        See this for applications of Huffman Coding.
//        There are mainly two major parts in Huffman Coding
//
//        Build a Huffman Tree from input characters.
//        Traverse the Huffman Tree and assign codes to characters.
//        Steps to build Huffman Tree
//        Input is an array of unique characters along with their frequency of occurrences and output is Huffman Tree.
//
//        Create a leaf node for each unique character and build a min heap of all leaf nodes (Min Heap is used as a priority queue. The value of frequency field is used to compare two nodes in min heap. Initially, the least frequent character is at root)
//        Extract two nodes with the minimum frequency from the min heap.
//
//        Create a new internal node with a frequency equal to the sum of the two nodes frequencies. Make the first extracted node as its left child and the other extracted node as its right child. Add this node to the min heap.
//        Repeat steps#2 and #3 until the heap contains only one node. The remaining node is the root node and the tree is complete.
//

//import java.util.PriorityQueue;
//        import java.util.Scanner;
//        import java.util.Comparator;
//
//class Huffman {
//
//    // recursive function to print the
//    // huffman-code through the tree traversal.
//    // Here s is the huffman - code generated.
//    public static void printCode(HuffmanNode root, String s)
//    {
//
//        // base case; if the left and right are null
//        // then its a leaf node and we print
//        // the code s generated by traversing the tree.
//        if (root.left
//                == null
//                && root.right
//                == null
//                && Character.isLetter(root.c)) {
//
//            // c is the character in the node
//            System.out.println(root.c + ":" + s);
//
//            return;
//        }
//
//        // if we go to left then add "0" to the code.
//        // if we go to the right add"1" to the code.
//
//        // recursive calls for left and
//        // right sub-tree of the generated tree.
//        printCode(root.left, s + "0");
//        printCode(root.right, s + "1");
//    }
//
//    // main function
//    public static void main(String[] args)
//    {
//
//        Scanner s = new Scanner(System.in);
//
//        // number of characters.
//        int n = 6;
//        char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f' };
//        int[] charfreq = { 5, 9, 12, 13, 16, 45 };
//
//        // creating a priority queue q.
//        // makes a min-priority queue(min-heap).
//        PriorityQueue<HuffmanNode> q
//                = new PriorityQueue<HuffmanNode>(n, new MyComparator());
//
//        for (int i = 0; i < n; i++) {
//
//            // creating a Huffman node object
//            // and add it to the priority queue.
//            HuffmanNode hn = new HuffmanNode();
//
//            hn.c = charArray[i];
//            hn.data = charfreq[i];
//
//            hn.left = null;
//            hn.right = null;
//
//            // add functions adds
//            // the huffman node to the queue.
//            q.add(hn);
//        }
//
//        // create a root node
//        HuffmanNode root = null;
//
//        // Here we will extract the two minimum value
//        // from the heap each time until
//        // its size reduces to 1, extract until
//        // all the nodes are extracted.
//        while (q.size() > 1) {
//
//            // first min extract.
//            HuffmanNode x = q.peek();
//            q.poll();
//
//            // second min extract.
//            HuffmanNode y = q.peek();
//            q.poll();
//
//            // new node f which is equal
//            HuffmanNode f = new HuffmanNode();
//
//            // to the sum of the frequency of the two nodes
//            // assigning values to the f node.
//            f.data = x.data + y.data;
//            f.c = '-';
//
//            // first extracted node as left child.
//            f.left = x;
//
//            // second extracted node as the right child.
//            f.right = y;
//
//            // marking the f node as the root node.
//            root = f;
//
//            // add this node to the priority-queue.
//            q.add(f);
//        }
//
//        // print the codes by traversing the tree
//        printCode(root, "");
//    }
//}
//
//// node class is the basic structure
//// of each node present in the Huffman - tree.
//class HuffmanNode {
//
//    int data;
//    char c;
//
//    HuffmanNode left;
//    HuffmanNode right;
//}
//
//// comparator class helps to compare the node
//// on the basis of one of its attribute.
//// Here we will be compared
//// on the basis of data values of the nodes.
//class MyComparator implements Comparator<HuffmanNode> {
//    public int compare(HuffmanNode x, HuffmanNode y)
//    {
//
//        return x.data - y.data;
//    }
//}
//


