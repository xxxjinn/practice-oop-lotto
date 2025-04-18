package view;

import camp.nextstep.edu.missionutils.Console;


public class InputViewImpl implements InputView {
    @Override
    public int getPurchaseAmount() {
        System.out.println("구입 금액을 입력해주세요.");
        String input = Console.readLine();
        return Integer.parseInt(input);
    }

    @Override
    public String getWinningNumber() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return Console.readLine().trim();
    }

    @Override
    public int getBonusNumber() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        String input = Console.readLine();
        return Integer.parseInt(input);
    }
}
