import java.io.*;
import java.util.Scanner;

public class CheckDemo  {

    public static void main(String[] args) {
    	String sourcePath;
        String targetPath;
        String ansPath;
    	@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        System.out.println("请输入原文文件:");
        sourcePath = input.nextLine();
        System.out.println("请输入抄袭版论文的文件:");
        targetPath = input.nextLine();
        System.out.println("请输入答案文件:");
        ansPath = input.nextLine();
        
        long startTime=System.currentTimeMillis();
        
        String source = disposeFile(sourcePath);
        String target = disposeFile(targetPath);

//        String source = disposeFile("F:\\RJGC\\test\\orig.txt");
//        String target = disposeFile("F:\\RJGC\\test\\orig_0.8_add.txt");

        float similaryRate = Levenshtein_Distance(source, target);
        String similary = String.format("%.1f", similaryRate);
        System.out.println("文章相似度为" + (similary) + "%");
        
        // 鏉堟挸鍤粵鏃�顢嶉弬鍥︽
        File file = new File(ansPath);
        try {
            Writer writer = new FileWriter(file,false);
            writer.write((sourcePath) + "跟" + (targetPath) + "的");
            writer.write("文章相似度为   " + (similary) + "%");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("总计用时"+(endTime-startTime)+"ms");
    }

    public static float Levenshtein_Distance(String source, String target){

        int[][] matrix;
        int srcLen = source.length();
        int targetLen = target.length();
        int i,j;
        int temp;

        if (srcLen == 0 || targetLen == 0){
            return 0;
        }

        matrix = new int[srcLen + 1][targetLen + 1];

        for (i = 0; i <= srcLen; i++){//初始化第一行
            matrix[i][0] = i;
        }

        for (j = 0; j <= targetLen; j++){//初始化第一列
            matrix[0][j] = j;
        }

        for (i = 1; i <= srcLen; i++){//
            for (j = 1; j <= targetLen; j++){
                if (source.charAt(i -1) == target.charAt(j - 1)){
                    temp = 0;
                }else {
                    temp = 1;
                }

                matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), 
                						matrix[i - 1][j - 1] + temp);
            }
        }

        return (1 - (float) matrix[srcLen][targetLen] / Math.max(source.length(), target.length())) * 100F;
    }

    public static String disposeFile(String filePath){

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader;
        try {
        	reader = new BufferedReader(new FileReader(filePath));
            String s;
            while((s = reader.readLine()) != null){
                buffer.append(s.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] splitAddress = buffer.toString().split("[^0-9a-zA-Z\\u4e00-\\u9fa5]");
        StringBuilder result = new StringBuilder();
        for (String address : splitAddress) {
        	result.append(address);
        }

        return  result.toString();
    }

}
