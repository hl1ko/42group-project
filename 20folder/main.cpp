#include <iostream>
#include <string>

int printMenu(int &x, bool &enabled){
    std::cout << "All hail Ken Tsang.\n";
    std::cout << "*** SMS Main Menu ***\n";
    std::cout << "[1] Load Starting Data\n";
    std::cout << "[2] Show Student Records\n";
    std::cout << "[3] Add/Delete Students\n";
    std::cout << "[4] Edit Students\n";
    std::cout << "[5] Generate Transcript\n";
    std::cout << "[6] Credits and Exit\n";
    std::cout << "*************\n";
    std::cout << "Option (1 - 6): ";

    std::cin >> x; //get user input
    if (!(enabled) && ((x >= 2) || (x <= 5)) && (x != 1)){ 
        // show error only when data is not loaded and user using function 2~5. R1.2
        std::cout << "Starting data is not yet loaded. Please load starting data first!\n";
        x = 0;
    }else if(!(enabled) && (x == 1)){
        enabled = true;
    }
    return x; // return user input
    // we should rewrite this actually.s
}

class studentdata{
    public:

    private:
    char name[30];
    char id[7];
    char major[30];
    float gpa;
    char subjectname[7];
    int credit;
    char grade[2];

};







int main(void){
    int response = 999; // set variable to hold user input;
    bool boo = false;
    do{
        printMenu(response, boo);
        switch (response)
        {
        case 1:
            std::cout << response << std::endl;
            boo = true;
            break;
        case 2:
            std::cout << response << std::endl;
            break;
        case 3:
            std::cout << response << std::endl;
            break;
        case 4:
            std::cout << response << std::endl;
            break;
        case 5:
            std::cout << response << std::endl;
            break;
        case 6:
            std::cout << response << std::endl;
            break;
        default:
            std::cout << response << std::endl;
            break;
        }
        system("pause");
        system("cls");
        //need better solution
    }while(response != 6);
    return 0;
}