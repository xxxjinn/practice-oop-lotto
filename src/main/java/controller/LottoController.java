package controller;

import domain.calculator.LottoResultCalculator;
import domain.calculator.ProfitCalculator;
import dto.LottoDto;
import global.enums.ErrorMessage;
import model.BonusNumber;
import model.Lotto;
import model.LottoGenerator;
import model.PurchaseAmount;
import model.constants.WinningRank;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private Lotto winningLotto;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        PurchaseAmount purchaseAmount = handlePurchaseAmount();
        int numberOfLotto = purchaseAmount.calculateNumberOfLotto();
        outputView.printPurchaseCount(numberOfLotto);

        List<Lotto> lottoNumbers = LottoGenerator.generateMultiple(numberOfLotto);
        List<LottoDto> lottoDto = convertToDto(lottoNumbers);
        outputView.printLottoNumbers(lottoDto);

        winningLotto = getWinningNumbers();
        BonusNumber bonusNumber = handleBonusNumber();

        Map<WinningRank, Integer> result = LottoResultCalculator.calculate(lottoNumbers, winningLotto, bonusNumber);
        outputView.printWinningResult(result);

        float profitRate = ProfitCalculator.calculate(result, purchaseAmount.getAmount());
        outputView.printProfitRate(profitRate);
    }

    private PurchaseAmount handlePurchaseAmount() {
        while (true) {
            try {
                int amount = inputView.getPurchaseAmount();
                return new PurchaseAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println(ErrorMessage.ERROR_MESSAGE_PREFIX.getMessage() + ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage());
            }
        }
    }

    private List<Integer> parseNumbers(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private Lotto getWinningNumbers() {
        while (true) {
            try {
                String input = inputView.getWinningNumber();
                List<Integer> numbers = parseNumbers(input);
                return new Lotto(numbers);
            } catch (NumberFormatException e) {
                System.out.println(ErrorMessage.ERROR_MESSAGE_PREFIX.getMessage() + ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage());
            }
        }
    }

    private List<LottoDto> convertToDto(List<Lotto> lottoList) {
        return lottoList.stream()
                .map(lotto -> new LottoDto(lotto.getLottoNumbers()))
                .collect(Collectors.toList());
    }

    private BonusNumber handleBonusNumber() {
        while (true) {
            try {
                int bonusNumber = inputView.getBonusNumber();
                return new BonusNumber(bonusNumber, winningLotto);
            } catch (NumberFormatException e) {
                System.out.println(ErrorMessage.ERROR_MESSAGE_PREFIX.getMessage() + ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage());
            }
        }
    }
}
