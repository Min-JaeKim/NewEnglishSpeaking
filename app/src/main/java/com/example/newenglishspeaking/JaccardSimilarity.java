package com.example.newenglishspeaking;

import java.util.ArrayList;

public class JaccardSimilarity {

    public static int getJarkard(String str1, String str2){
        ArrayList<String> list1 = new ArrayList<String>();  //답안
        ArrayList<String> list2 = new ArrayList<String>();  //모범 답안

        //문자열을 잘라서 다중 집합을 만든다.
        String tmp = "";
        for(int i = 0; i< str1.length()-1; i++){
            tmp = str1.substring(i, i+2).toUpperCase(); //대소문자 구분 없애기 (대문자로 만든다)
            if(tmp.matches("^[A-Z]+$")){    //영문자일 경우 리스트에 추가
                list1.add(tmp);
            }
        }
        for(int i = 0; i<str2.length()-1; i++){
            tmp = str2.substring(i, i+2).toUpperCase();
            if(tmp.matches("^[A-Z]+$")){
                list2.add(tmp);
            }
        }

        //합집합 만들기
        ArrayList<String> unionList = getUnionList(list1, list2);

        //교집합 만들기
        ArrayList<String> intersectionList = getIntersectionList(list1, list2);

        //교집합 원소수 / 합집합 원소수 * 100 = 유사도 (%)
        if(intersectionList.size()==0) return 0;
        else return (int) ((double) intersectionList.size()/(double) unionList.size()*(double)100);
    }

    //다중집합의 합집합 구하기
    private static ArrayList<String> getUnionList(ArrayList<String> list1, ArrayList<String> list2){
        list1 = (ArrayList<String>) list1.clone();
        list2 = (ArrayList<String>) list2.clone();

        ArrayList<String> unionList = new ArrayList<String>();
        for(String str: list1){
            if(list2.contains(str)){
                list2.remove(str);
            }
            unionList.add(str);
        }
        unionList.addAll(list2);
        return unionList;
    }

    //다중집합의 교집합 구하기
    private static ArrayList<String> getIntersectionList(ArrayList<String> list1, ArrayList<String> list2){
        list1 = (ArrayList<String>) list1.clone();
        list2 = (ArrayList<String>) list2.clone();

        ArrayList<String> interList = new ArrayList<String>();
        for(String str:list1){
            interList.add(str);
            list2.remove(str);
        }
        return interList;
    }
}
