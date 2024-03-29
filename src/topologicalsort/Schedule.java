//
// Schedule.java
//
// This class encapsulates functionality for constructing a directed acyclic graph 
// and running a topological sort.
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

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Schedule
{
    //
    // Program entry point:
    //
    public static void main(String[] args)
    {
        int status = 0;
             
        //
        // Check if args contains only one element (file name):
        //
        if (args.length != 1)
        {
            status = -1;
        }
        
        //
        // Create buffered file reader:
        //
        File           stringFile     = null;
        FileReader     fileReader     = null;
        BufferedReader bufferedReader = null;
        String         line           = null;
        
        if (status == 0)
        {
            try
            {
                stringFile     = new File(args[0]);
                fileReader     = new FileReader(stringFile);
                bufferedReader = new BufferedReader(fileReader);
            }
            catch (IOException ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
        
        //
        // Read node count from file:
        //
        int nodeCount = 0;
        
        if (status == 0)
        {
            try
            {
                line = bufferedReader.readLine();
                
                if (line == null)
                {
                    status = -1;
                }
                else
                {
                    nodeCount = Integer.parseInt(line);
                }
            }
            catch (IOException ex)
            {
                System.err.println(ex);
                status = -1;
            }
            catch (Exception ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
        
        //
        // Initialize an array of nodes, a node map and an adjacency matrix:
        //
        Node[]            nodes           = null;
        Map<String, Node> nodeMap         = null;
        boolean[][]       adjacencyMatrix = null;
        
        if (status == 0)
        {
            try
            {
                nodes           = new Node[nodeCount];
                nodeMap         = new HashMap<String, Node>();
                adjacencyMatrix = new boolean[nodeCount][nodeCount];
            }
            catch (Exception ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
               
        //
        // Read node names from file and construct nodes:
        //
        if (status == 0)
        {
            try
            {
                for (int i = 0; i < nodes.length; ++i)
                {
                    line = bufferedReader.readLine();
                    
                    if (line == null)
                    {
                        status = -1;
                        break;
                    }
                    
                    nodes[i] = new Node(i + 1, line);
                    nodeMap.put(line, nodes[i]);
                }
            }
            catch (IOException ex)
            {
                System.err.println(ex);
                status = -1;
            }
            catch (Exception ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
        
        //
        // Read edge count from file:
        //
        int edgeCount = 0;
        
        if (status == 0)
        {
            try
            {
                line = bufferedReader.readLine();
                
                if (line == null)
                {
                    status = -1;
                }
                else
                {
                    edgeCount = Integer.parseInt(line);
                }
            }
            catch (IOException ex)
            {
                System.err.println(ex);
                status = -1;
            }
            catch (Exception ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
        
        //
        // Initialize an array of edges (nodes) of length edgeCount:
        //
        Node[] edges = null;
        
        if (status == 0)
        {
            try
            {
                edges = new Node[edgeCount];
            }
            catch (Exception ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
        
        //
        // Read edges from file and construct edge nodes:
        //
        if (status == 0)
        {
            try
            {
                for (int i = 0; i < edges.length; ++i)
                {
                    line = bufferedReader.readLine();
                    
                    if (line == null)
                    {
                        status = -1;
                        break;
                    }
                    
                    String[] edgeNodes = line.split(",", 2);
                    
                    Node from = nodeMap.get(edgeNodes[0]);
                    Node to   = nodeMap.get(edgeNodes[1]);
                    
                    if (from == null || to == null)
                    {
                        status = -1;
                        break;
                    }
                    
                    edges[i] = new Node(from.value, to);
                }
            }
            catch (IOException ex)
            {
                System.err.println(ex);
                status = -1;
            }
            catch (Exception ex)
            {
                System.err.println(ex);
                status = -1;
            }
        }
        
        //
        // Set adjacency matrix:
        //
        if (status == 0)
        {
            for (int i = 0; i < edges.length; ++i)
            {
                Node edge = edges[i];
                
                adjacencyMatrix[edge.value - 1][edge.next.value - 1] = true;
            }
        }
        
        //
        // Sort, set names and print order:
        //
        Node startNode = TopologicalSort.tsortDFS(adjacencyMatrix, 1);
        Node tempStart = startNode;
        
        while (tempStart != null)
        {
            tempStart.name = nodes[tempStart.value].name;
            
            tempStart = tempStart.next;
        }
        
        System.out.println(startNode);
    }
}
