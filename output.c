#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void foo(int x){
printf("%d\n", x);
}
void baz(){
printf("%s\n", "Hello World");
}
char* bar(double x){
if (x>5.1)
{return "bar";
}
baz();
return "foo";
}
int main(void){
double y;
int x;
x = 5;
y = 1.0;
while (x>0)
{foo(x);
printf("%s\n", bar(y));
x = x-1;
y = y+1.1;
}
baz();
}
