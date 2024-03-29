//
// Node.java
//
// This class encapsulates the attributes and behavior of a single node in a singely-linked list.
//
// The MIT License (MIT)
// 
// Copyright (c) 2022 Luke Andrews.  All Rights Reserved.
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this
// software and associated documentation files (the "Software"), to deal in the Software
// without restriction, including without limitation the rights to use, copy, modify, merge,
// publish, distribute, sub-license, and/or sell copies of the Software, and to permit persons
// to whom the Software is furnished to do so, subject to the following conditions:
// 
// * The above copyright notice and this permission notice shall be included in all copies or
// substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
// INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
// PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
// FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
// DEALINGS IN THE SOFTWARE.
//
package topologicalsort;

class Node
{
    public int    value;
    public String name;
    public Node   next;
    
    public Node()
    {
        //do nothing
    }
    
    public Node(int value)
    {
        this.value = value;
        this.name = "";
    }
    
    public Node(int value, String name)
    {
        this.value = value;
        this.name = name;
    }
    
    public Node(int value, Node next)
    {
        this.value = value;
        this.name = "";
        this.next = next;
    }
    
    public String toString()
    {
        StringBuilder ret = new StringBuilder();
        
        Node curr = this;
        
        while(curr != null)
        {
            ret.append(curr.name);
            
            if (curr.next != null)
            {
                ret.append(" -> ");
            }
            
            curr = curr.next;
        }
        
        return ret.toString();
    }
}
