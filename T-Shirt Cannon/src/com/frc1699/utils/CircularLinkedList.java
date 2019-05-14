package com.frc1699.utils;

public class CircularLinkedList {

    private Node head;
    private Node tail;

    private Node currentNode = head; //Used for iterator

    public CircularLinkedList(){
        head = null;
    }

    public void createNodeAtEnd(Object data){
        Node node = new Node(data);
        
        if(head == null){
            head = node;
            tail = node;
            return;
        }

        tail.next = node;
        tail = node;
        tail.next = head;
    }

    //Use for debugging only
    public void printList(){
        Node currentNode = head;
        while(currentNode.next != null){
            currentNode = currentNode.next;
        }
    }

    public boolean hasNext() {
        return head != null && tail != null; //Because circular will always have next unless null
    }

    public Node next() {
        Node returnNode = currentNode.next;
        currentNode = currentNode.next;
        return returnNode;
    }

    public static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
            next = null;
        }

        public Object getData(){
            return data;
        }

        public String toString() {
            return data.toString();
        }
    }
}