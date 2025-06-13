package org.example;

import entity.Team;
import org.example.repository.TeamRepository;
import org.example.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class TeamServiceConcurrencyTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void testWysciguWatkow() throws InterruptedException {
        // 1. tworzymy team do testu, zeby miec czysty start
        Team testTeam = transactionTemplate.execute(status -> {
            Team team = new Team("Konfliktowi", "Testowo", "Stara Arena", "Y");
            return teamRepository.save(team);
        });
        Integer teamId = testTeam.getId();

        // 2. latch zeby watki wystartowaly rowno
        int iloscWatkow = 2;
        ExecutorService service = Executors.newFixedThreadPool(iloscWatkow);
        CountDownLatch latch = new CountDownLatch(iloscWatkow);
        AtomicBoolean wystapilWyjatek = new AtomicBoolean(false);

        // 3. odpalamy 2 watki, ktore beda sie scigac
        for (int i = 0; i < iloscWatkow; i++) {
            int nrWatku = i + 1;
            service.execute(() -> {
                try {
                    // kazdy watek dziala we wlasnej transakcji
                    transactionTemplate.execute(status -> {
                        // pobieramy najswiezsza wersje z bazy
                        Team teamWatku = teamRepository.findById(teamId).orElseThrow();
                        // kazdy watek probuje ustawic inna nazwe areny
                        teamWatku.setArena("Zaktualizowane przez watek " + nrWatku);
                        teamRepository.save(teamWatku);
                        return null;
                    });
                } catch (ObjectOptimisticLockingFailureException e) {
                    // jak zlapalismy blad to super, o to chodzilo
                    // ustawiamy flage, zeby glowny watek o tym wiedzial
                    wystapilWyjatek.set(true);
                } finally {
                    // tak czy siak, watek skonczyl robote
                    latch.countDown();
                }
            });
        }

        // 4. czekamy az oba watki skoncza
        latch.await();

        // SPRAWDZENIE
        // 5. sprawdzamy czy na pewno byl wyjatek
        await().untilTrue(wystapilWyjatek);
        assertThat(wystapilWyjatek.get()).isTrue();

        // 6. jeszcze sprawdzamy czy w bazie jest wpis tego watku ktory wygral
        Team finalTeam = teamRepository.findById(teamId).orElseThrow();
        assertThat(finalTeam.getArena()).startsWith("Zaktualizowane przez watek");
    }
}