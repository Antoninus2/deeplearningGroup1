//
//  main.c
//  SemesterProject
//
//  Created by iMac on 4/9/19.
//  Copyright � 2019 iMac. All rights reserved.
//

#include <stdio.h>
#include <strings.h>
#include "listFunctions.h"
#include "applicationHeader.h"
#include "savingprojectInfo.h"


void projMenu (void);
void edit(LINKED_LIST * listPtr, void * data);
void delete_descr(LINKED_LIST * listPtr);
void save(LINKED_LIST * list, void * dataPtr);

void getInfoFromUser(PROJECT_NODE * nPTR);
void editInfoFromUser(PROJECT_NODE * nPTR);
void editProjectNameFromUser(PROJECT_NODE * nPTR);
void editProjectFromUser(PROJECT_NODE * nPTR);
void editProjectDateFromUser(PROJECT_NODE * nPTR);
void editProjectDueDateFromUser(PROJECT_NODE * nPTR);
void editProjectMembers(PROJECT_NODE * nPTR);
void displayHeaderFile(void*);
void readTxt(void);
void delete(LINKED_LIST * list);
void writetoFile(LINKED_LIST * listPtr, FILE* filePtr);
void savingData(LINKED_LIST * listPtr);
LIST_NODE* search(LINKED_LIST * head, int id);

PROJECT_NODE * nodePtr = NULL;
LINKED_LIST list;

//  list of names (character strings)

int main(int argc, char *argv[]) {

    projMenu();
   
    
}

void projMenu (void) {

    int choice;
    //char *projcName = NULL;
    

    list.size = 0;
    list.front = list.back = NULL;  // the list needs to have a front and back
   // memberList.
   // memberList.size = 0;
    
    do {
    printf("\nMenu\n");
    printf("1. Create a Project\n");
    printf("2. Edit an Existing Project\n");
    printf("3. Display Current Projects\n");
    printf("4. Delete a Project\n");
    printf("5. Save to the file.\n");
    printf("6. Read the file and print.\n")
    printf("6. Quit\n");
    printf("Enter your choice: ");
    scanf("%d", &choice);

    

    switch (choice) {
        case 1:
            nodePtr = (PROJECT_NODE *) calloc(1, sizeof(PROJECT_NODE)); // make space for one structure with all your info
            getInfoFromUser(nodePtr);    // getting that information from the user with the pointer
            printf("%p\n", list.front);
            append(&list, nodePtr);           // this is how you add elements to the list
            //printf("Size of list is:", list.size);
            traverseForward(list, displayHeaderFile); // knows only to display
            break;
        case 2:
            editInfoFromUser(nodePtr);
            break;
        case 3:
            traverseForward(list, displayHeaderFile);
            break;
        case 4:
            delete(&list);
            break;
        case 5:
            readTxt();
            break;
        case 6:
            savingData(&list);
            break;
        case 7:
            break;
        default: printf("Enter a number between 1-5:\n");
            break;
    }
    } while (choice != 6);
    return;
}

void getInfoFromUser(PROJECT_NODE * nPTR){
    
    unsigned int id;
    char ProjName[101];
    char ProjDesr[1001];
    char ProjDate[10];
    char ProjDueDate[10];
    unsigned int num_Members;
    
    printf("Enter the id: ");
    scanf("%d", &id);
    
    nPTR -> projID = id;
    
    printf("Enter project name: ");
    scanf("%s", ProjName);
    
    strcpy(nPTR -> projecName, ProjName);  // its copying the string of the project name into the struct
    
    printf("Enter project description: ");
    scanf("%s", ProjName);

    strcpy(nPTR -> projDescr, ProjDesr);
    
    printf("Enter Date (mmddyyyy): ");
    scanf("%s", ProjDate);
    
    strcpy(nPTR -> projDate, ProjDate);
    
    printf("Enter due Date: (mmddyyyy) ");
    scanf("%s", ProjDueDate);
    
    strcpy(nPTR -> projDueDate, ProjDueDate);
    
    printf("Enter number of Members: ");
    scanf("%d", &num_Members);
    
    nPTR -> num_Members = num_Members;
    
    printf("\n");
    
    
}

void editInfoFromUser(PROJECT_NODE * nPtr)
{
    int choice = 0;
    int iD = 0;
    //int i = 0;
    printf("Which project do you want to edit?");
    scanf("%d", &iD);
    LIST_NODE * pPtr;
    pPtr = search(&list, iD);
    
    if(pPtr == NULL)
    {
        printf("\nID number not found.\n");
        return;
    }

    do{
        nPtr = (PROJECT_NODE *)pPtr;
        
        printf("ChangeMenu\n");
        printf("1. Edit name:\n");
        printf("2. Edit project: \n");
        printf("3. Edit date: \n");
        printf("4. Edit due date:\n");
        printf("5. Edit project members: \n");
        printf("6. Back\n");
        printf("Enter your choice:");
        scanf("%d", &choice);
        
        
        switch (choice) {
            case 1:    editProjectNameFromUser(nPtr);
                break;
            case 2:    editProjectFromUser(nPtr);
                break;
            case 3:    editProjectDateFromUser(nPtr);
                break;
            case 4:    editProjectDueDateFromUser(nPtr);
                break;
            case 5:    editProjectMembers(nPtr);
                break;
            case 6:
                break;
            default: printf("Wrong Choice. Enter again\n");
                break;
        }
    }while (choice != 5);
    return;
}


void editProjectNameFromUser(PROJECT_NODE * nPTR)
{
     char ProjName[101];
    
    printf("Enter project name: ");
    scanf("%s", ProjName);
    
    strcpy(nPTR -> projecName, ProjName);
    
}
           
void editProjectFromUser(PROJECT_NODE * nPTR)
{
    char ProjDesr[1001];
    printf("Enter project description: ");
    scanf("%s", ProjDesr);
    
    strcpy(nPTR -> projDescr, ProjDesr);
    

}

void editProjectDateFromUser(PROJECT_NODE * nPTR)
{
    char ProjDate[10];
    strcpy(nPTR -> projDate, ProjDate);
    printf("Enter due Date (mmddyyyy): ");
    scanf("%s", ProjDate);
    
}

void editProjectDueDateFromUser(PROJECT_NODE * nPTR)
{
    char ProjDueDate[10];
    strcpy(nPTR -> projDate, ProjDueDate);
    printf("Enter due Date (mmddyyyy: ");
    scanf("%s", ProjDueDate);
    
}

void editProjectMembers(PROJECT_NODE * nPTR)
{
    unsigned int num_Members;
    
    printf("Enter number of Members: ");
    scanf("%d", &num_Members);
    nPTR -> num_Members = num_Members;
    printf("\n");
    
}


void displayHeaderFile(void * node) // this funtions tells it what to do with the node to print these guys
{
    
        struct projectNode * head = (struct projectNode*) node; // that casts because node is a void pointer but you cannot deference a void pointer
        printf("%d) %s \n", head->projID, head->projecName);
    
}
        
// search the list sign up for the funcitons

LIST_NODE* search(LINKED_LIST * listPtr, int id)
{
    if (listPtr == NULL || listPtr->front == NULL) // Initialize current
        return NULL;
    
    LIST_NODE * curr = listPtr-> front;
    
    while (curr) {
        struct projectNode * node = (struct projectNode *) curr->dataPtr;
         if (node->projID == id)
             return curr->dataPtr;
        
             curr = curr->next;
         
        
    }
    return NULL;
}

void delete(LINKED_LIST * list)
{
    
    int iD = 0;
    //int i = 0;
    printf("Enter ID for the node do you want to delete:");
    scanf("%d", &iD);
    LIST_NODE * cur  = search(list, iD);
    
    if(cur == list->front){
        removeFromFront(list);
        return;
    }
    
    if(cur == list->back){
        removeFromBack(list);
        return;
    }
    
    LIST_NODE * delete = cur;
    cur = cur->next;
    cur->prev = delete->prev;
    cur = delete->prev;
    cur->next=delete->next;
    
    delete->next = delete->prev = NULL;
    struct projectNode * Ptr = (struct projectNode*) delete->dataPtr;
    deleteList(Ptr->memberList);
    free(delete);
    
}



void savingData(LINKED_LIST * listPtr)
{
    //make a file using username as the title and putting the project information on it
    
    //save one data  at a time
    printf("blobl\n");
    FILE * filePtr= fopen("Projects.txt", "w");
    if (filePtr == NULL) {
        
        printf("File not found");
        return;
        //ferror(filePtr);
    }
    
    //printf("im here");
    writetoFile(listPtr, filePtr);
    fclose(filePtr);
}


void writetoFile(LINKED_LIST * listPtr, FILE* filePtr){
    
    //Go down the list
    //Put specific fwrites for each project info
    //Put delimiters
    char name[101], desc[1001], date[9], duedate[9];
    
    
    LIST_NODE * front = listPtr->front;
    printf("blobl\n");
    while(front)
    {
        printf("blup\n");
        struct projectNode * node = (struct projectNode *)front->dataPtr;
        
        //Writes project ID to Project.txt
        fwrite(&node->projID, sizeof(int), 1, filePtr);
        
        //Writes project name to Projects.txt
        fwrite(node->projecName, sizeof(name), 1, filePtr);
        
        
        //Writes project description to Projects.txt
        fwrite(node->projDescr, sizeof(desc), 1, filePtr);
        
        
        //Writes date to Projects.txt
        fwrite(node->projDate, sizeof(date), 1, filePtr);
        
        
        //Writes due date to Projects.txt
        fwrite(node->projDueDate, sizeof(duedate), 1, filePtr);
        
        
        //Writes number of memebers to Projects.txt
        fwrite(&node->num_Members,sizeof(int),1, filePtr);
        fputc(0, filePtr);
        
        front = front->next;
        
    }
    
}

#define MAXCHAR 1000
void readTxt()
{
    FILE *fp;
    char str[MAXCHAR];
    char* filename = "/Users/imac/Desktop/SemesterProject/SemesterProject/SemesterProject-gugzyreumpawphbqmbtfizzmblvz/Build/Products/Debug/Projects.txt";
    
    fp = fopen(filename, "r");
    if (fp == NULL){
        printf("Could not open file %s",filename);
        return;
    }
    while (fgets(str, MAXCHAR, fp) != NULL)
        printf("%s", str);
    fclose(fp);
    return;
}
