////////////////////////////////////////////////////////////////////////////////
////   Ramsan M M M
////   100435M
////   ENTC
////////////////////////////////////////////////////////////////////////////////
package algomini;

class Node{       
    //single link list
    //only it has next node
    private String data; // data item                 
    public Node next; // next link in node
    // -------------------------------------------------------------------------
    
    public Node(String data1){ // node constructor
        data= data1; 
    }
    // -------------------------------------------------------------------------
    
    public String getString(){ //return node data
        return data; 
    }
    // -------------------------------------------------------------------------
    
    public int displayNode(String name,int index,int i){ // display the noe rating
        
        String temp[]=data.split(" "); // split the current node data
        if(name.equals(temp[index])){  //check whether the name is same with data
            if(index==2)System.out.print(temp[5]+" ");   //index 2--book; so print book rating at 5th place
            else if(index==3)System.out.print(temp[4]+" ");  //index 3--vendor; so print vendor rating at 4th place
            i++;  //it just to count       
        }      
        return i;   //return count
    }
       
}
//==============================================================================
//==============================================================================

class LinkedList{
    
    private Node first; // referenc to first node
    private int linkSize; //size at a hase value
    private boolean moreUser;  //user checking
    // -------------------------------------------------------------------------
    
    public void LinkedList(){ // Linked list constructor
         first = null;
         linkSize=0;
         moreUser=false;
    }
    // -------------------------------------------------------------------------
    
    public void insert(Node theNode){// insert node, in sorted order(by date)
        
        linkSize++;  //increase linksize
        String key = theNode.getString();
        Node previous = null; // start at first
        Node current = first;
        
        // until end of list and check date
        while( current != null && !dateCopare(key, current.getString())){
            previous = current;
            current = current.next;  // go to next node
        }
        if(previous==null) // if beginning of node
            first = theNode;  // first --> new node
        else // not at beginning,
            previous.next = theNode; // prev --> new node
        
        theNode.next = current; // new node --> current
    }
    //--------------------------------------------------------------------------
    
    public void insertUnsorted(Node theNode){  // insert link, in unsorted manner    
        
        linkSize++;  //increase link size
        if(linkSize>1){
            String user1 = theNode.getString();   //it is just to verify as the single hash table
            String user2 = first.getString();     //contain only one user
            String temp1[]=user1.split(" ");      //it is not used for insert 
            String temp2[]=user2.split(" ");      //but used to countUser function
            if(!temp1[1].equals(temp2[1]))
                moreUser=true;                
        }
        
        Node previous = null; // start at first
        Node current = first;
        
        if(previous==null) // if beginning of node,
            first = theNode; // first --> new node
        else // not at beginning,
            previous.next = theNode; // prev --> new node
        
        theNode.next = current; // new node --> current
    }
    //--------------------------------------------------------------------------
    
    public int countUser(String user){ //count nodes at single userHashTable
        
        if(moreUser==false) return linkSize; //if only one user just return
        else{
            int newCount=0;
            Node current = first; // start at beginning of node
            while(current != null){ // until end of list,            
                String data=current.getString(); // get data
                String temp[]=data.split(" ");
                if(temp[1].equals(user)) //check with user name
                    newCount++;          //if user name is exsist then increase count
                current = current.next;  // move to next link
            } 
            return newCount;
        }
    }
    //--------------------------------------------------------------------------
    
    public int size(){
        return linkSize;  //return linkSize
    } 
   //---------------------------------------------------------------------------
     
    public String ithData(int i){  //ith data at a single hash table
        Node current = first;   // start at beginning of node
        for(int j=0;j<i;j++){
            current = current.next; // move to next node
        }
        return current.getString();     
    }
    //-------------------------------------------------------------------------- 
    
    public void displayRate(String name,int index){ //display 5 most resent ratings
        //it may be chance that a single hash table has less than 5 nodes
        //so if we call 5 nodes, then it is compilation error
        //so introduce another variable    
        int newSize;
        if(linkSize<5)  //if link size is less than 5, rate numbers of consisting nodes
            newSize=linkSize;
        else newSize=5;  //consisting more than 5
        
        Node current = first; // start at beginning of node
        int i=0;
        System.out.print("Most recent ratings: ");
        while(current != null){ // until end of node        
             i=current.displayNode(name,index,i); // print rating             
             if(i==newSize) break;
             current = current.next; // move to next node
        }       
    }
    //--------------------------------------------------------------------------    
          
    public long StringToLong(String data1){
        //2013-01-15T22:45 textuser Introduction_to_Algorithms abcpublishers 3 4
        
        String temp1[]=data1.split(" ");       //split the data by space      
        String date11[]=temp1[0].split("T");   //2013-01-15T22:45 is spilited by "T"
        String date1[]= date11[0].split("-");  //2013-01-15 is spilited by "-"
        String time1[]= date11[1].split(":");  //22:45 is spilited by ":"
        
        String stringDate=date1[0]+date1[1]+date1[2]+time1[0]+time1[1];
        //add the strings as a single string 
        return Long.parseLong(stringDate);   ///parse the string to long
    }
    //--------------------------------------------------------------------------
    
    public boolean dateCopare(String data1,String data2){
        
        if(StringToLong(data1)>StringToLong(data2))  // return true if date1 > date 2 
            return true;
        else return false;
    }
}
