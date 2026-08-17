# Spring Async Self-Invocation Debugging Lab

`@Async`を付けたメソッドを同じSpring beanから直接呼び出すと、Futureは正常に返っても処理が呼び出し元スレッドで同期実行される不具合を再現します。

## 実行

Java 21とMavenを前提に、次を実行します。

```bash
mvn test
```

バグ状態では、`submit`の結果自体は`sent:invoice-1`になりますが、ワーカースレッド名が`main`のままです。修正後は`task-1`などの別スレッドになります。

## 履歴

| コミット | 内容 |
| --- | --- |
| `4d5667e` | バグ再現。自己呼び出しにより`@Async`が適用されない。 |
| 修正コミット | `AsyncWorker`を分離し、proxy経由で呼び出す。 |

Springのproxy境界を通る呼び出しだけがアドバイスの対象になるため、キャッシュ教材とは異なる観測点として「実行スレッド」を固定しています。
