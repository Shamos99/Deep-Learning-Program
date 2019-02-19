import numpy as np
import os

class LoadData:

    def __init__(self):
        self.rawfile = os.path.dirname(os.path.abspath(__file__))+"/data.txt"
        self.lettersfile = os.path.dirname(os.path.abspath(__file__))+"/letter.txt"

    def loadData(self):
        return (self._loadRaw(),self._loadletters())

    def _loadRaw(self):

        try:
            with open(self.rawfile) as f:
                file_data = f.read()
        except FileNotFoundError:
            raise FileNotFoundError("File could not be found")

        file_data = (file_data.split("&"))

        allData = []

        for list in file_data:

            temp = list.split('\n')

            final = []

            for i in range(len(temp)):
                temp[i] = temp[i].split(" ")

            for row in temp:
                for number in row:
                    final.append(int(number))

            allData.append(np.array(final))

        return np.array(allData)

    def _loadletters(self):

        try:
            with open(self.lettersfile) as f:
                file_data = f.read()
        except FileNotFoundError:
            raise FileNotFoundError("File could not be found")

        file_data = file_data.split("\n")
        file_data.pop(len(file_data)-1)

        actual = []

        for letter in file_data:
            actual.append(ord(letter)-65)

        actual = np.array(actual)

        return actual



if __name__ == "__main__":

    test = LoadData()
    (x,y) = test.loadData()
    l = []
    for elem in x[17]:
        l.append(str(elem))

    string = ""

    for j in l:
        string+=str(j)+','


    with open('test.txt','w+') as f:
        f.write(string)





