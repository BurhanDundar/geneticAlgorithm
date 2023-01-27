
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author burhandundar
 */
public class GeneticAlgo {

    class Member { // emin değilim burdan ? 
        //public String[] chromosome;

        ArrayList<String> chromosome = new ArrayList<String>();
        private int fitness;

        public Member(ArrayList<String> chromosome) { // popülasyondaki nesneleri tanımlayan member classı
            this.chromosome = chromosome; // genlerden(karakterlerden) oluşan dizi
            this.fitness = 0; // hedefe kaç karakter uzakta olunduğunu gösteren değişken
        }
    }
    public static int generationMean = 0;
    public String genePool; // karakter havuzu
    public String target; // aranan kelime
    public Integer populationNumber; // popülasyondaki member sayısı
    public int staticPopulationNumber;
    public Integer targetLength; // aranan kelime uzunluğu
    ArrayList<Member> population = new ArrayList<Member>();
    ArrayList<Member> nextGeneration = new ArrayList<Member>();
    public Boolean Found;
    public Integer GenerationTimer;
    ArrayList<String> foundText = new ArrayList<String>();

    public GeneticAlgo(String target, Integer populationNumber) {
        this.genePool = "abcçdefgğhıijklmnoöpqrsştuüvwxyzABCÇDEFGĞHİIJKLMNOÖPQRŞSTUÜVWXYZ 1234567890,.-;:_!'#%&/()=?@${[]}"; // çift tırnak yerine geçici olarak tek tırnak koydum
        this.target = target; 
        this.targetLength = this.target.length();
        this.populationNumber = populationNumber;
        this.staticPopulationNumber = populationNumber;
        this.population = population;
        this.nextGeneration = nextGeneration;
        this.Found = false;
        this.GenerationTimer = 0;
    }

    public String randomGene() { // gen havuzundan rastgele karakter seçilmesi
        int randomNumber = new Random().nextInt(this.genePool.length());
        String randomGeneAsString = String.valueOf(this.genePool.charAt(randomNumber));
        return randomGeneAsString;
    }

    public ArrayList<String> createChromosome() { // rastgele seçilen genler ile kromozom (gen dizisi) oluşturulması
        ArrayList<String> chromosome = new ArrayList<String>();
        for (int i = 0; i < this.targetLength; i++) {
            chromosome.add(this.randomGene());
        }
        return chromosome;
    }

    public void calculateFitness() { // hedefle mevcut kromozom arasındaki karakter farkı sayısı
        for (Member member : this.population) {
            member.fitness = 0;
            for (int i = 0; i < this.targetLength; i++) {
                if (member.chromosome.get(i).equals(String.valueOf(this.target.charAt(i)))) {
                    member.fitness++;
                }
            }

            if (member.fitness == this.targetLength) {
                this.foundText = member.chromosome;
                this.Found = true;
            }
        }
    }

    public void crossover() { // kromozomların çaprazlanması
        int lastBest = (90 * this.populationNumber) / 100; 
        this.nextGeneration.removeAll(this.nextGeneration);
        this.nextGeneration.addAll(this.population.subList(lastBest, this.population.size())); // fitness değerlerine göre sıralanmış dizinin %90 ile %100 arasındaki elemanlarını alma işlemi
        System.out.println(this.nextGeneration.get(this.staticPopulationNumber/ 10 -1).chromosome);
        while (true) { // bir sonraki jenerasyon sayısı şu anki popülasyon sayısına ulaştırana kadar seçilim işlemleri yapılacak
            if (this.nextGeneration.size() < this.populationNumber) {
                int randomNumber = new Random().nextInt(lastBest, this.population.size());
                int randomNumber2 = new Random().nextInt(lastBest, this.population.size());

                ArrayList<String> member1 = this.population.get(randomNumber).chromosome;
                ArrayList<String> member2 = this.population.get(randomNumber2).chromosome;
                ArrayList<String> heritageMember = new ArrayList<String>();

                for (int k = 0; k < member1.size(); k++) { //% 0-47 aralığında ilk member seçilir
                    Random randomObj = new Random();
                    double prob = randomObj.nextDouble();
                    if (prob < 0.47) {
                        heritageMember.add(member1.get(k));
                    } else if (prob < 0.94) { //% 47 - 94 aralığında ikinci member seçilir
                        heritageMember.add(member2.get(k));
                    } else { //%6 ihtimalle mutasyon meydana gelir
                        heritageMember.add(this.randomGene());
                    }
                }
                Member member = new Member(heritageMember);
                this.nextGeneration.add(member);
            } else {
                break;
            }
        }
        this.population = (ArrayList<Member>) this.nextGeneration.clone();
    }

    public void runAlgo() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.populationNumber; i++) {
            Member member = new Member(this.createChromosome());
            this.population.add(member);
        }
        System.out.println("Her Neslin En iyi Kromozomları:");
        while (!this.Found) { // aranan kelime bulunana kadar alttaki işlemler gerçekleşecek
            this.calculateFitness(); // uyumluluk hesabı
            this.population.sort((Member m1, Member m2) -> new Integer(m1.fitness).compareTo(m2.fitness)); // uyumluluk değerlerine göre popülasyondaki member'ların sıralanması
            this.crossover(); // sıralanan memberlerin crossover ile bir sonraki nesle aktarılması
            this.GenerationTimer++;
        }
        long end = System.currentTimeMillis();
        System.out.println("Aradığınız " + this.foundText + ", kelimesini " + this.GenerationTimer + " nesilde buldunuz. " + "\nAradığınız kelime = " + this.target);         
        this.generationMean = this.generationMean + this.GenerationTimer;
        System.out.println("İşlem süresi: "+ (end-start) + " ms");

    }
}
