package model;

import global.enums.ErrorMessage;
import global.enums.MagicNumber;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = Collections.unmodifiableList(numbers);
    }

    private void validate(List<Integer> numbers) {
        validateLength(numbers);
        validateRange(numbers);
        validateDuplicate(numbers);
    }

    private void validateLength(List<Integer> numbers) {
        if (numbers.size() != MagicNumber.LOTTO_LENGTH.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBER_LENGTH.getMessage());
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int number : numbers) {
            if (number < MagicNumber.LOTTO_NUMBER_MIN.getValue() || number > MagicNumber.LOTTO_NUMBER_MAX.getValue()) {
                throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBER_RANGE.getMessage());
            }
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        for (int number : numbers) {
            if (!uniqueNumbers.add(number)) {
                throw new IllegalArgumentException(ErrorMessage.WINNING_NUMBER_IS_DUPLICATE.getMessage());
            }
        }
    }

    // TODO: 추가 기능 구현
    public List<Integer> getLottoNumbers() {
        return numbers;
    }
}
