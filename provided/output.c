#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(void){
char *sewer;
char *x;
sewer = malloc(sizeof(char)*26);
strcpy(sewer,"Underground Lizard Gang");
x = strcat(sewer, ", Rats");
printf("%s\n", x);
}
