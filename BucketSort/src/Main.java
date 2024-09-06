import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static final String FILE_NAME = "dados500_mil.txt";

    public static void main(String[] args) throws IOException {
        int[] array = {1, 2, 3};

        if (array != null) {
            long opInicio, opFim;
            int[] movimentos = {0};
            int[] comparacoes = {0};

            opInicio = System.currentTimeMillis();

            bucketSort(array, movimentos, comparacoes);

            opFim = System.currentTimeMillis();
            String tempo_validado = calcTempo(opFim - opInicio);

            System.out.println("Array Ordenado: " + Arrays.toString(array));
            System.out.println("Aluno: Marcelo");
            System.out.println("Algoritmo: BucketSort");
            System.out.println("Tempo de Execucao: " + tempo_validado);
            System.out.println("Quantidade de comparações: " + comparacoes[0]);
            System.out.println("Quantidade de movimentos: " + movimentos[0]);
        } else {
            System.out.println("Array Não encontrado");
        }
    }

    public static void bucketSort(int[] array, int[] movimentos, int[] comparacoes) {
        int valMin = array[0];
        int valMax = array[0];

        for (int i = 0; i < array.length; i++) {
            valMin = Math.min(array[i], valMin);
            valMax = Math.max(array[i], valMax);
        }

        ArrayList<ArrayList<Integer>> buckets = criaBucket(array);

        for (int valor : array) {
            int classificacao = classificaNumero(valor, valMin, valMax, buckets.size());
            buckets.get(classificacao).add(valor);
        }

        for (ArrayList<Integer> bucket : buckets) {
            insertionSort(bucket, movimentos, comparacoes);
        }

        int indexArray = 0;
        for (ArrayList<Integer> bucket : buckets) {
            for (int valor : bucket) {
                array[indexArray++] = valor;
            }
        }
    }

    public static void insertionSort(ArrayList<Integer> array, int[] movimentos, int[] comparacoes) {
        for (int i = 1; i < array.size(); i++) {
            int valorAtual = array.get(i);
            int j = i - 1;
            while (j >= 0 && array.get(j) > valorAtual) {
                array.set(j + 1, array.get(j));
                j--;
                movimentos[0]++;
                comparacoes[0]++;
            }
            if (j >= 0) {
                comparacoes[0]++;
            }
            array.set(j + 1, valorAtual);
        }
    }

    public static ArrayList<ArrayList<Integer>> criaBucket(int[] array) {
        int qtdBuckets = (int) Math.sqrt(array.length);
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(qtdBuckets);

        for (int i = 0; i < qtdBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        return buckets;
    }

    public static int classificaNumero(int num, int valMin, int valMax, int numBuckets) {
        double normalizacao = (double) (num - valMin) / (valMax - valMin);
        return (int) (normalizacao * (numBuckets - 1));
    }

    private static int[] readDataFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int[] data = new int[500000];
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null && index < data.length) {
            try {
                data[index++] = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.err.println("Warning: Skipping invalid data line: " + line);
            }
        }
        reader.close();
        return data;
    }

    public static String calcTempo(long total) {
        long opHr, opMin, opSeg, opMils;

        opMils = total % 1000;
        total /= 1000;
        opHr = total / 3600;
        total %= 3600;
        opMin = total / 60;
        opSeg = total % 60;

        return opHr + ":" + opMin + ":" + opSeg + ":" + opMils;
    }
}