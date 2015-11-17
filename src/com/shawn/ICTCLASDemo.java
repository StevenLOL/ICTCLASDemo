package com.shawn;

/**
 * Author:             Shawn Guo
 * E-mail:             air_fighter@163.com
 *
 * Create Time:        2015/10/16 09:40
 * Last Modified Time: 2015/10/26 10:07
 *
 * Class Name:         ICTCLASDemo
 * Class Function:
 *                     Ŀǰ����˻�����ܵĿ�������Ŀ¼�¶���question.txt�е����⣬Ȼ���Զ�
 *                     �����з�Ϊ��ɺ�ѡ������ɹ�ϵ��������������ȹظ�����飻��������
 *                     �ɴ�����ѡ���������ƶȣ�ѡ�����ƶ�������Ϊ�𰸡�
 *                     ��ϵ�����ԡ�֪ʶͼ�ס���Ϊ����������ͨ����֪ʶͼ���п������õ���ظ��
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ICTCLASDemo {

    public Options[] options = new Options[5];
    public HashMap<String, Concept> kGraph = new HashMap<>();

    public void init() throws IOException, ClassNotFoundException {
        String inputString = BasicIO.readFile2String("question.txt");
        inputString += "E. ";

        for (int i = 0; i < 5; i++) {
            options[i] = new Options(inputString.split((char)Integer.sum(65, i) + ".")[0]);
            inputString = inputString.split((char)Integer.sum(65, i) + ".")[1];
        }

    }

    public void printAnswer(int i) {
        if (i == 0) {
            System.out.println("C" + "(δ�ܸ����𰸣��ô����ɵġ�)");
        }
        else {
            System.out.println( (char)Integer.sum(64, i) );
        }
    }

    public static void main (String[] args) throws Exception {
        ICTCLASDemo self = new ICTCLASDemo();
        self.init();
        KGraph kgraph = new KGraph();
        self.kGraph = kgraph.getkGraphFromXML();
        //System.out.println(self.kGraph);



        SimilarityComputer similarityComputer = new SimilarityComputer();
        RelationAnalyzer relationAnalyzer = new RelationAnalyzer();
        int maxSimilarityIndex = 0;
        double maxSimilarityValue = 0.0;
        double similarity = 0.0;

        HashSet<String> compareSet0 = new HashSet<>(self.options[0].words);
        compareSet0.addAll(self.options[0].relatedConcepts);

        for (int i = 0; i < 5; i++) {

            self.options[i].relatedConcepts = relationAnalyzer.getRelatedConceptSet(self.options[i].words, self.kGraph);
            HashSet<String> compareSet = new HashSet<>(self.options[i].words);
            compareSet.addAll(self.options[i].relatedConcepts);

            similarity = similarityComputer.getSimilarity(compareSet0, compareSet);

            if(i != 0 && similarity > maxSimilarityValue) {
                maxSimilarityIndex = i;
                maxSimilarityValue = similarity;
            }

//            System.out.println("Option #" + i + ":");
//            System.out.println("\tDictionary Keys��" + self.options[i].words);
//            System.out.println("\tRelated Concepts: " + self.options[i].relatedConcepts);
//            System.out.println("\tCompare Set: " + compareSet);
//            System.out.println("\tSimilarity to question: " + similarity);
        }

        self.printAnswer(maxSimilarityIndex);

    }
}
