//
// TopologicalSort.java
//
// This class encapsulates functionality for performing a topological sort on a directed acyclic graph.
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

class TopologicalSort
{
    //
    // TSort Implementation #1: Depth-First Search
    //
    // Uses DFS to create a topological sort. It accepts a graph stored as an adjacency matrix 
    // and returns null for invalid inputs for a topological sort.
    //
    public static Node tsortDFS(boolean[][] graph, int startNode)
    {
        //
        // Error checking on inputs, requires graph, valid starting node, and square matrix:
        //
        if(startNode < 0 || startNode > graph.length-1)
        {
            return null;
        }
        
        for (int i = 0; i < graph.length; i++)
        {
            if (graph[i].length != graph.length)
            {
                return null;
            }
        }
        
        //
        // Indicators for whether or not a node has been visited:
        //      0 = not started
        //     -1 = started not finished
        //      1 = finished
        //
        int[] visited = new int[graph.length];
        
        //
        // The topological ordering:
        //
        Node sortedOrder = new Node(); //dummy head
        
        boolean dfsDone = false; // initialize DFS
        
        while(!dfsDone)
        {
            //
            // Visit the start node:
            //
            if (!visit(graph, startNode, visited, sortedOrder))
            {
                return null; //graph has no topological sorting
            }
            
            //
            // Make sure all the nodes have been visited:
            //
            dfsDone = true;
            
            for (int i = 0; i < graph.length; i++)
            {
                if (visited[i] == 0)
                {
                    startNode = i;
                    dfsDone = false;
                    break;
                }
            }
        }
        
        //
        // Return the topological sorting:
        //
        return sortedOrder.next; //skip dummy head
    }
    
    //
    // TSort implementation #2: Removal-Based
    //
    // Uses the removal method to create a topological sort. It accepts a graph stored as an adjacency list
    // and returns null for invalid inputs for a topological sort:
    //
    public static Node tsortRemoval(Node[] graph)
    {
        //
        // Error checking on inputs - requires graph and nodes to only point within the graph:
        //
        for (int i = 0; i < graph.length; i++)
        {
            Node curr = graph[i];
            
            while (curr != null)
            {
                if (curr.value < 0 || curr.value > graph.length-1)
                {
                    return null;
                }
                
                curr = curr.next;
            }
        }
        
        //
        // A count of the number of incoming edges for each node:
        //
        int[] numIncoming = new int[graph.length];
        
        for (int i = 0; i < graph.length; i++)
        {
            Node curr = graph[i];
            
            while (curr != null)
            {
                numIncoming[curr.value]++;
                curr = curr.next;
            }
        }
        
        //
        // The set of nodes with no incoming edges:
        //
        Node setNoIncoming = new Node(); //dummy head
        
        for (int i = 0; i < numIncoming.length; i++)
        {
            if (numIncoming[i] == 0)
            {
                setNoIncoming.next = new Node(i, setNoIncoming.next);
            }
        }
        
        //
        // The topological ordering:
        //
        int  numSorted       = 0;
        Node sortedOrder     = new Node(); //dummy head
        Node sortedOrderTail = sortedOrder; //need a tail for appending
        
        //
        // While there are nodes with no incoming edges:
        //
        while (setNoIncoming.next != null)
        {
            //
            // Get one node and remove it from the set:
            //
            int nodeId = setNoIncoming.next.value;
            setNoIncoming.next = setNoIncoming.next.next;
            
            //
            // Add it to the topological sorting:
            //
            sortedOrderTail.next = new Node(nodeId);
            sortedOrderTail = sortedOrderTail.next;
            numSorted++;
            
            //
            // For each node it's connected to, remove one incoming edge:
            //
            Node curr = graph[nodeId];
            
            while (curr != null)
            {
                if (--numIncoming[curr.value] == 0)
                {
                    //
                    // If there are no more incoming edges,
                    // add this to the set of nodes with no incoming edges:
                    //
                    setNoIncoming.next = new Node(curr.value, setNoIncoming.next);
                }
                
                curr = curr.next;
            }
        }
        
        //
        // There must have been a cycle somewhere:
        //
        if (numSorted != graph.length)
        {
            return null;
        }
        
        return sortedOrder.next; //skip dummy head
    }
    
    //
    // Helper method for visiting each node of a depth-first search.
    // 
    //
    private static boolean visit(boolean[][] graph, int currentNode, int[] visited, Node sortedOrder)
    {
        //
        // Mark current node as started:
        //
        visited[currentNode] = -1;
        
        //
        // Visit each neighbor who hasn't been visited before:
        //
        for (int i = 0; i < graph.length; i++)
        {
            //
            // If "i" is a neighbor of the current node...
            //
            if(graph[currentNode][i])
            {
                //
                // Check if there's a cycle:
                //
                if(visited[i] == -1)
                {
                    return false;
                }
                
                //
                // Try to visit neighbor if not visited, but stop if neighbor encounters a cycle:
                //
                if (visited[i] != 1 && !visit(graph, i, visited, sortedOrder))
                {
                    return false;
                }
            }
        }
        
        //
        // Node is finished, mark finished and prepend to topological sorting:
        //
        visited[currentNode] = 1;
        sortedOrder.next = new Node(currentNode, sortedOrder.next);
        
        return true;
    }
}
