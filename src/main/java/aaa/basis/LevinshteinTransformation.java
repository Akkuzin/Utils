package aaa.basis;

import static aaa.lambda.LambdaUtils.peek;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

public class LevinshteinTransformation {

	public enum OperationType {
		INSERT, DELETE, REPLACE, SWAP
	}

	public interface IOperation<T> {
		Function<List<T>, List<T>> getAction();
	}

	public static abstract class BaseOperation<T> implements IOperation<T> {
		private OperationType operationType;

		public BaseOperation(OperationType operationType) {
			this.operationType = operationType;
		}

		@Override
		public String toString() {
			return operationType.name();
		}
	}

	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@ToString
	public static class ReplaceOperation<T> extends BaseOperation<T> {
		int place;
		T newValue;
		@Getter
		Function<List<T>, List<T>> action;

		public ReplaceOperation(int place, T newValue) {
			super(OperationType.REPLACE);
			this.place = place;
			this.newValue = newValue;
			this.action = peek(list -> {
				list.remove(place);
				list.add(place, newValue);
			});
		}
	}

	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@ToString
	public static class InsertOperation<T> extends BaseOperation<T> {
		int place;
		T newValue;
		@Getter
		Function<List<T>, List<T>> action;

		public InsertOperation(int place, T newValue) {
			super(OperationType.INSERT);
			this.place = place;
			this.newValue = newValue;
			this.action = peek(list -> list.add(place, newValue));
		}
	}

	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@ToString
	public static class DeleteOperation<T> extends BaseOperation<T> {
		int place;
		@Getter
		Function<List<T>, List<T>> action;

		public DeleteOperation(int place) {
			super(OperationType.DELETE);
			this.place = place;
			this.action = peek(list -> list.remove(place));
		}
	}

	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@ToString
	public static class SwapOperation<T> extends BaseOperation<T> {
		int place;
		@Getter
		Function<List<T>, List<T>> action;

		public SwapOperation(int place) {
			super(OperationType.SWAP);
			this.place = place;
			this.action = peek(list -> list.add(place + 1, list.remove(place)));
		}
	}

	@AllArgsConstructor
	@Builder
	public static class LevenshteinParameters {
		int insertWeight;
		int deleteWeight;
		int replaceWeight;
		int swapWeight;
	}

	public static LevenshteinParameters DEFAULT_PARAMETERS = LevenshteinParameters.builder()
			.insertWeight(10)
			.deleteWeight(10)
			.replaceWeight(19)
			.swapWeight(19)
			.build();

	public static <T> Collection<IOperation<T>> levenshteinDistance(List<T> source,
																	List<T> target,
																	LevenshteinParameters params) {
		int sourceLength = source.size();
		int targetLength = target.size();
		int[][] distance = new int[sourceLength + 1][targetLength + 1];
		OperationType[][] operations = new OperationType[sourceLength + 1][targetLength + 1];

		distance[0][0] = 0;
		for (int i = 1; i <= sourceLength; ++i) {
			distance[i][0] = distance[i - 1][0] + params.deleteWeight;
			operations[i][0] = OperationType.DELETE;
		}
		for (int j = 1; j <= targetLength; ++j) {
			distance[0][j] = distance[0][j - 1] + params.insertWeight;
			operations[0][j] = OperationType.INSERT;
		}

		for (int j = 1; j <= targetLength; ++j) {
			for (int i = 1; i <= sourceLength; ++i) {
				int deleteCost = distance[i - 1][j] + params.deleteWeight;
				int insertCost = distance[i][j - 1] + params.insertWeight;
				int replaceCost =
						distance[i - 1][j - 1]
							+ (source.get(i - 1) == target.get(j - 1) ? 0 : params.replaceWeight);
				int swapCost =
						(i > 1 && j > 1 && source.get(i - 1) == target.get(j - 2) // 
						&& source.get(i - 2) == target.get(j - 1)) //
																	? distance[i - 2][j - 2]
																		+ params.swapWeight
																	: Integer.MAX_VALUE;

				distance[i][j] =
						Collections.min(asList(deleteCost, insertCost, replaceCost, swapCost));
				if (distance[i][j] == deleteCost) {
					operations[i][j] = OperationType.DELETE;
				} else if (distance[i][j] == insertCost) {
					operations[i][j] = OperationType.INSERT;
				} else if (distance[i][j] == swapCost) {
					operations[i][j] = OperationType.SWAP;
				} else if (distance[i][j] == replaceCost) {
					operations[i][j] = OperationType.REPLACE;
				}
			}
		}

		List<IOperation<T>> result = new ArrayList<>(distance[sourceLength][targetLength]);
		int i = sourceLength;
		int j = targetLength;
		while (i > 0 || j > 0) {
			int curDist = distance[i][j];
			OperationType curOperation = operations[i][j];
			IOperation<T> operation = null;
			switch (curOperation) {
			case SWAP:
				i -= 2;
				j -= 2;
				operation = new SwapOperation<>(i);
				break;
			case REPLACE:
				--i;
				--j;
				operation = new ReplaceOperation<>(i, target.get(j));
				break;
			case DELETE:
				--i;
				operation = new DeleteOperation<>(i);
				break;
			case INSERT:
				--j;
				operation = new InsertOperation<>(i, target.get(j));
				break;
			}
			if (curDist != distance[i][j]) {
				result.add(operation);
			}
		}
		return result;
	}
}
