////////////////////////////////////////////////////////////////////////////////
////   Ramsan M M M
////   100435M
////   ENTC
////////////////////////////////////////////////////////////////////////////////
package algomini;

//import files
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author ramsan
 */
public class Main {
        
        HashTable bookHashTable = new HashTable(250);
        HashTable vendorHashTable = new HashTable(250);
        HashTable userHashTable = new HashTable(250);
        
    public static void main(String[] args) {
                
        Main rating=new Main();
        Node bookNode,vendorNode,userNode;  //create nodes to add book, vendor and user
        Scanner scan=new Scanner(System.in);
        
        
        ///////////////////////////////file reading//////////////////////////////////////              
        FileInputStream fstream = null;
        //exception handelling  
        try {           
            fstream = new FileInputStream("data.txt");  //store the input to data.txt
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;            
          
            while ((strLine = br.readLine()) != null) {
                bookNode=new Node(strLine); //create book node by adding the detail
                vendorNode=new Node(strLine); //create vendor node by adding the detail
                userNode=new Node(strLine);  //create user node by adding the detail
                
                //2013-01-15T22:45 textuser Introduction_to_Algorithms abcpublishers 3 4
                rating.bookHashTable.insert(bookNode,2);  //insert the booknode to book hashtable
                rating.vendorHashTable.insert(vendorNode,3); //inset to vendor hash table
                rating.userHashTable.insert(userNode,1); //insert to user hash table                                       
            }            
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
        ///////////////////////////////end file reading////////////////////////////////////
        
        //run continously        
        while(true){
        
            System.out.println("Enter first letter of Book Search(b/B),Vendor Search(v/V) or exit(e/E)");
            String choice = scan.nextLine(); //read the input
            
            //choose according to the selection
            if(choice.charAt(0)=='b' || choice.charAt(0)=='B'){
                System.out.println("Enter book name:");
                String book=scan.nextLine();                
                float rate=rating.bookAggregateRating(book);  //call bookAggregateRating(bookName) function              
                //it may be chace that there is no book user need
                //if we call dirrectly the fuctions then compilation error
                if(rate>=0){
                    System.out.printf("\nBook name: %s\n",book);
                    rating.bookHashTable.displayResentRating(book,2); //call displayResentRating(bookName,indexOfBook)
                    System.out.printf("\nAggregate rating of book: %.3f\n",rate); //print rating
                    System.out.println("Top rated vendors of the book:");
                    rating.topRatedVendors(book);   //call topRatedVendors(bookName), it is in sorted order(by rating)                
                }  
                else
                    System.out.println("NOT AVAILABLE"); //error message
                
                 System.out.println("");                         
            }
            
            else if(choice.charAt(0)=='v' || choice.charAt(0)=='V'){
                System.out.println("Enter vendor name:");
                String vendor=scan.nextLine();
                float rate=rating.vendorAggregateRating(vendor);  //call vendorAggregateRating(vendorName) function
                //it may be chace that there is no vendor user prefer
                //if we call dirrectly the fuctions then compilation error
                if(rate>=0){
                    System.out.printf("\nVendor name: %s\n",vendor);
                    rating.vendorHashTable.displayResentRating(vendor,3);  //call displayResentRating(vendorName,indexOfVendor)
                    System.out.printf("\nAggregate rating of vendor: %.3f\n",rate); //print rating
                    System.out.println("List of products that vendor sells and rating: ");
                    ////call listOfProducts(vendorName), it is not in sorted order(by rating)
                    ////but it will be printed resent books which belongs to vendor
                    rating.listOfProducts(vendor); 
                }         
                else
                    System.out.println("NOT AVAILABLE"); //error message
                    
                System.out.println("");         
            }
            else if(choice.charAt(0)=='e' || choice.charAt(0)=='E')  //terminate the while loop
                break;
            else{
                System.out.println("Invalied input");  //invalied sellection
                System.out.println("");
            }
        }        
    
    }
    
    public float bookAggregateRating(String name){   ///////// book/vendor name   index of book
        
        int hasValue=userHashTable.hashFunc(name);  //hash value
        int noBooks=bookHashTable.noElements(hasValue); //the numbers of contents at this hasvalue
        float num=0;
        float den=0;
        
        for(int i=0;i<noBooks;i++){
            String data=bookHashTable.input(hasValue, i); //get the input string
            String temp[]=data.split(" ");
            //split data to compare with book name 
            //because it may be chance to store different book name with same hash value
            //Afbcy_Aixyl_Ywlql and Upopc_Cjkdm both have same hash value
            //so first check with exact book name then only allow to consider for rating calculation
            if(temp[2].equals(name)){  
                //number of times a user rated books and vendors
                float n=userHashTable.hashUserCount(temp[1],userHashTable.hashFunc(temp[1]));                
                float w= (float)(2-(1/n));
                
                int r=Integer.parseInt(temp[5]);   //book rating
                
                num+=w*r;
                den+=w;              
            }            
        }
        if(num==0)  //because to check whether the book avilable or not
            return -1;  //if there is no book, it returns some negative value
        else return num/den;     
        
    }
    
    
    public float vendorAggregateRating(String name){ 
        
        int hasValue=userHashTable.hashFunc(name);  //hash value
        int noVendors=vendorHashTable.noElements(hasValue);  //the numbers of contents at this hasvalue
        float num=0;
        float den=0;
        
        for(int i=0;i<noVendors;i++){
            String data=vendorHashTable.input(hasValue, i);  //get the input
            String temp[]=data.split(" "); 
            //split data to compare with vendor name 
            //because it may be chance to store different vendor name with same hash value
            //Afbcy_Aixyl_Ywlql and Upopc_Cjkdm both have same hash value
            //so first check with exact vendor name
            if(temp[3].equals(name)){  
                //number of times a user rated books and vendors
                float n=userHashTable.hashUserCount(temp[1],userHashTable.hashFunc(temp[1]));                
                float w= (float)(2-(1/n)); //weight of user
                
                int r=Integer.parseInt(temp[4]);     //vendor rating                
                num+=w*r;
                den+=w;          
            }            
        }       
        if(num==0) //because to check wether the book avilable or not
            return -1;  //if there is no book, it returns some negative value
        else return num/den;
        
    }
    
    
   public void listOfProducts(String vendor){ //books that vendor sells
       String[] temp=vendorHashTable.vendorLinkListCopy(vendor);
       int i=0;
       while(temp[i]!=null){  
           ////it is not in sorted order(by rating)
           ////but it will be printed resent books which belongs to vendor
           System.out.printf("  %.3f  :  %s\n",bookAggregateRating(temp[i]),temp[i]);           
           i++;
       }       
   }
   
   public void topRatedVendors(String book){ //it prints in sorted order(by rating)
       String[] temp=bookHashTable.bookLinkListCopy(book);
       int arraySize=bookHashTable.noElements(bookHashTable.hashFunc(book))+1; //define array size
       float[] rate=new float[arraySize];
       int i=0;
       
       while(temp[i]!=null){
           rate[i]=vendorAggregateRating(temp[i]);   //assign rating for vendors       
           i++;
       }       
       //it is most optimal bubble sort
       int n=i;
       int newLimit=0;
       boolean swapped;
       do{   //it runs atleast once
           swapped=false;
           for(int k=1;k<n;k++){
               if(rate[k-1]<rate[k]){   //swappe the vendor name too when the rating of venders are swapped
                   //sort the rating     //swapping parrallely vendors and rating
                   float just=rate[k];  
                   rate[k]=rate[k-1];  // exchange the ratings
                   rate[k-1]=just;
                   //sort the vendors
                   String tempString=temp[k];
                   temp[k]=temp[k-1];    // exchange the vendors according to rating exchanges
                   temp[k-1]=tempString;
                   
                   swapped=true;    ///if swapped then true
                   newLimit=k;           
                   //reduce the for loop because during first iteration the smallest go to last 
                   // so need consider that again
               }
           }
           n=newLimit;
       }while(swapped);
      //print in sorted order according to rating
       for(int j=0;j<i;j++){
           System.out.printf("  %.3f  :  %s\n",rate[j],temp[j]);           
       }
   }  
}
