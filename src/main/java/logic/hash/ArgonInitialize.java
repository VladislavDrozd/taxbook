package logic.hash;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

 public class ArgonInitialize {

    private final int iterations;
    private final int parallelism = 2;
    private final int memory = 65536;
    private final int time = 1000;
    private final Argon2 argon2;
    private static ArgonInitialize instance;

    public int getIterations() {
        return iterations;
    }

    public int getParallelism() {
        return parallelism;
    }

    public int getMemory() {
        return memory;
    }

    public int getTime() {
        return time;
    }

     public Argon2 getArgon2() {
         return argon2;
     }

     private ArgonInitialize(){
        argon2 =  Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id,
                16,
                32);
        iterations = Argon2Helper.findIterations(argon2, time, memory, parallelism);
     }

     public static ArgonInitialize getInstance(){
        if(instance == null){
            instance = new ArgonInitialize();
        }
        return instance;
     }
}
