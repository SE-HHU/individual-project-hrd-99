import random

n = int(input())
Answers = open("Answers.txt", "w", encoding="UTF-8")
Exercises = open("Exercises.txt", "w", encoding="UTF-8")
list = ["", ]
for x in range(n):
    isTwoSignal = random.choice([True, False])
    if (isTwoSignal):
        while (True):
            a = random.randint(0, 100)
            b = random.randint(0, 100)
            c = random.randint(0, 100)
            ch1 = random.choice('+-')
            ch2 = random.choice('+-')
            temp = f"{a} {ch1} {b} {ch2} {c}"
            if (ch1 == '+'):
                result = a + b
            else:
                result = a - b
            if (ch2 == '-'):
                result -= c
            else:
                result += c
            if (result > 0 & (temp not in list)):
                list.append(temp)
                break
        Exercises.write(f"{x + 1}. {a} {ch1} {b} {ch2} {c} =\n")
        Answers.write(f"{x + 1}. {a} {ch1} {b} {ch2} {c} = {result}\n")
    else:
        while (True):
            a = random.randint(0, 100)
            b = random.randint(0, 100)
            if (b > a):
                temp = a
                a = b
                b = temp
            ch1 = random.choice('+-')
            result = 0
            temp = f"{a} {ch1} {b}"
            if (ch1 == '+'):
                result = a + b
            else:
                result = a - b
            if temp not in list:
                list.append(temp)
                break
        Exercises.write(f"{x + 1}. {a} {ch1} {b}  =\n")
        Answers.write(f"{x + 1}. {a} {ch1} {b}  = {result}\n")
Answers.close()
Exercises.close()
