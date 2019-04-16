package com.dae.dragonball;

import java.util.Scanner;

/*
 * 동작을 입력한다
 * 0. 플레이어가 컴퓨터인지 사람인지 확인
 * 0-1. 컴퓨터라면 랜덤입력
 * 0-2. 사람이라면 스캐너
 * 1. 동작은 '기모으기' '막기' '공격'
 * 2. 기를 모으면 기++;
 * 3. 공격을 하면 기--;
 * 4. 기가 0개면 공격을 할 수 없다.
 * 5. 기를 모으는 동안 공격을 할 경우 체력 감소
 * 6. 체력이 0이 되면 게임 끝
 */

public class Play {

    private static Scanner scanner = new Scanner(System.in);;

    public void singlePlay(PlayerVO[] player) {

        // 이길 때 까지 반복
        while (true) {
            initStatus(player[0]);
            initStatus(player[1]);

            // 동작판단
            inputAction(player[0]);
            inputAction(player[1]);

            // 기 모으기
            // 공격하면 기 감소
            kiCount(player[0]);
            kiCount(player[1]);

            // 상대가 기를 모을때 공격하면 체력감소
            attack(player[0], player[1]);

            printMessage(player[1]);
            printMessage(player[0]);

            conWinner(player[0], player[1]);
            /*
             * TODO:버전업시 승리 이후 난이도가 상승하여 더 강한 상대 등장 승리 수 카운트 변수를 만들고, 상대 체력이 0이 되었을때, 위너
             * 시점에서 승리 수 카운트가 남아있으면 봇을 새로 생성
             */
            System.out.println();
            System.out.println("==================================================================");
            System.out.println("==================================================================");
            System.out.println("\n\n");

        }

    }

    public static void initStatus(PlayerVO player) {
        player.setHpStatus(false);
    }

    // 동작 판단
    public static void inputAction(PlayerVO player) {

        // 사람이면 동작 입력
        if (player.isPlayerBot() == false) {

            System.out.println("동작 입력");
            System.out.println("1.기모으기|2.막기|3.공격");
            System.out.println("----------------------");
            System.out.print("입력> ");
            player.setAction(scanner.nextInt());
            System.out.println();

            // 1,2,3외의 숫자는 잘못된 입력 --> 다시 시작
            if (player.getAction() < 1 || player.getAction() > 3) {
                System.out.println("잘못된 수 입력");
                inputAction(player);
            }

        }
        // bot이면 랜덤 동작
        else if (player.isPlayerBot()) {

            player.setAction((int) (Math.random() * 3) + 1);

        }

        // 기가 0이면 공격할 수 없다.
        if (player.getAction() == 3 && player.getKiCount() <= 0) {

            // 사람이면 메세지 출력
            if (player.isPlayerBot() == false) {
                System.out.println("기가 0입니다. 다시 입력하세요");
            }
            inputAction(player);
        }

    }

    // 기를 모으면 kiCount++, 공격하면 kiCount--
    public static void kiCount(PlayerVO player) {
        if (player.getAction() == 1) {
            player.setKiCount(player.getKiCount() + 1);
        } else if (player.getAction() == 3) {
            player.setKiCount(player.getKiCount() - 1);
        }
    }

    // 상대방이 기를 모을때 공격하면 체력 감소
    public static void attack(PlayerVO player1, PlayerVO player2) {
        /*
         * TODO:true-false로 상태 확인해서 체력감소 사실이 확인되면 메세지 출력하는 방식으로
         */
        if (player1.getAction() == 1 && player2.getAction() == 3) {

            player1.setHp(player1.getHp() - 1);
            player1.setHpStatus(true);

        } else if (player1.getAction() == 3 && player2.getAction() == 1) {

            player2.setHp(player2.getHp() - 1);
            player2.setHpStatus(true);

        }
    }

    public static void conWinner(PlayerVO player1, PlayerVO player2) {
        if (player1.getHp() == 0) {

            player2.setWin(true);
        } else if (player2.getHp() == 0) {
            player1.setWin(true);
        }

    }

    public static void printMessage(PlayerVO player) {
        System.out.println("\n");
        System.out.println(player);
        if (player.isHpStatus() == true) {
            System.out.println("////////" + player.getPlayerName() + " 체력 감소////////");
        }
        if (player.getHp() == 0) {
            System.out.println("////////" + player.getPlayerName() + " 사망////////");
        }
        if (player.isWin()) {
            System.out.println("++++++++" + player.getPlayerName() + " 승리++++++++");
        }

    }

}
