package com.likeyichu.train.libsvm;
import java.io.IOException;  

import org.junit.Test;
  
  
public class LibSVMTest {  
  
    public static void main(String[] args) throws IOException {
    	//参数依次是 train_set_file  model_file
        String[] trainArgs = {"-b","1","d:/libsvm/breast-cancer.train.txt","d:/libsvm/breast-cancer.model"};
        svm_train.main(trainArgs); 
        //参数依次是 test_file model_file output_file
        String[] testArgs = {"d:/libsvm/breast-cancer.predict.txt", "d:/libsvm/breast-cancer.model", "d:/libsvm/breast-cancer.predict.result.txt"};
        svm_predict.main(testArgs);  
    }  
    //@Test
    public void scaleTest() {
    	String[] scaleArgs = {"-s","d:/libsvm/breast-cancer.train.scale.txt","d:/libsvm/breast-cancer.train.txt"};
    	try {
			svm_scale.main(scaleArgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Test
    public void test() 
    {
    	 String[] testArgs = {"-b","1","d:/libsvm/gp.predict.txt", "d:/libsvm/gp.model", "d:/libsvm/gp.predict.result.txt"};
    	 try {
			svm_predict.main(testArgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
}  