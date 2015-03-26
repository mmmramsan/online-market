////////////////////////////////////////////////////////////////////////////////
////   Ramsan M M M
////   100435M
////   ENTC
////////////////////////////////////////////////////////////////////////////////
package algomini;

class HashTable{
    
    private LinkedList[] hashArray; // array of LinkedLists
    private int hashArraySize;  //hash table size
   //--------------------------------------------------------------------------- 
    
    public HashTable(int size){ // HashTable constructor
        hashArraySize = size;
        hashArray = new LinkedList[hashArraySize]; // create LinkedList
        for(int j=0; j<hashArraySize; j++) 
            hashArray[j] = new LinkedList(); // inizialize LinkedList
    }
    //--------------------------------------------------------------------------    
    
    public int hashFunc(String data,int index){ // hash function
        /// word  2013-01-15T22:45 textuser Introduction_to_Algorithms abcpublishers 3 4
        
        String temp[]=data.split(" ");  //split the data
        String word=temp[index];  
        
        int key=0;
        for(int i=0;i<word.length();i++){    //APPLE (65x1)+(80x2)+(80x3)+(76x4)+(69x5)=1114
             int letter=word.charAt(i);           
             key+=letter*(i+1);
         } 
        
        return key % hashArraySize;
    }
    //--------------------------------------------------------------------------
    
    public int hashFunc(String word){ // hash function
        /// word   Introduction_to_Algorithms //////abcpublishers
                             
        int key=0;
        for(int i=0;i<word.length();i++){    //APPLE (65x1)+(80x2)+(80x3)+(76x4)+(69x5)=1114
             int letter=word.charAt(i);        //ASCHI value A=65   
             key+=letter*(i+1);
         }  
        
        return key % hashArraySize;
    }    
    // -------------------------------------------------------------------------
    
    public void insert(Node theNode,int index){ // insert a link
    
        String key = theNode.getString();
        int hashVal = hashFunc(key,index); // hash the key
        if(index==1)
            hashArray[hashVal].insertUnsorted(theNode); // insert unsorted manner at hashVal
        else 
            hashArray[hashVal].insert(theNode); // insert at hashVal
    } // end insert()
    //--------------------------------------------------------------------------    
    
    public String[] bookLinkListCopy(String book){   //belogs to book hash table
        int key=hashFunc(book);  //hash value
        String[] temp=new String[noElements(key)+1];    //return item has the list of vendors
        int contents=0;  
        String temp1[]=input(key,0).split(" ");
        //it may be chance that the a different book with same hash value can be at same location
        //so first check is it a correct book same with book name
        //then add to array
        if(book.equals(temp1[2])){
            temp[0]=temp1[3]; //add first one 
            contents=1;
        }
        for(int i=1;i<noElements(key);i++){
            String temp2[]=input(key,i).split(" ");
             if(book.equals(temp2[2])){  //check with exact book name, if it true then it will access              
                boolean exsist=false; 
                for(int j=0;j<contents;j++){        //check vendor name as it is added early                
                        if(temp[j].equals(temp2[3])) // if it is added early no need add again
                            exsist=true;             
                }
                if(exsist==false){   //already not added to linklist
                    contents++;   
                    temp[contents-1]=temp2[3];
                }
             }
        }
        return temp;
    }
    //--------------------------------------------------------------------------
    
    public String[] vendorLinkListCopy(String vendor){  //belogs to vendor hash table
        int key=hashFunc(vendor);  //hash value
        String[] temp=new String[noElements(key)+1];   //return item has the list of vendors
        int contents=0;
        String temp1[]=input(key,0).split(" ");
        //it may be chance that the a different vendor with same hash value can be at same location
        //so first check is it a correct vendor same with vendor name
        //then add to array        
        if(vendor.equals(temp1[3])){
            temp[0]=temp1[2];   //add first one
            contents=1;
        } 
         
        for(int i=1;i<noElements(key);i++){
            String temp2[]=input(key,i).split(" ");
             if(vendor.equals(temp2[3])){   //check with exact vendor name, if it true then it will access             
                boolean exsist=false;
                for(int j=0;j<contents;j++){        //check book name as it is added early          
                        if(temp[j].equals(temp2[2])) // if it is added early no need add again
                            exsist=true;             
                }
                if(exsist==false){   //already not added to linklist
                    contents++;
                    temp[contents-1]=temp2[2];
                }
             }
        }
        return temp;
    }    
    //--------------------------------------------------------------------------
    
    public void displayResentRating(String name,int index){  
        int p=hashFunc(name);
        hashArray[p].displayRate(name,index); 
    }
    //--------------------------------------------------------------------------
    
    public int noElements(int key){  //number of nodes in key th place of hash table
        return hashArray[key].size();        
    }
    //--------------------------------------------------------------------------
    
    public String input(int key,int i){  //ith data on key th place of hash table 
        return hashArray[key].ithData(i);
    }
    //--------------------------------------------------------------------------
    
    public int hashUserCount(String user,int key){  //number of user
        return hashArray[key].countUser(user);
    }
    
}
