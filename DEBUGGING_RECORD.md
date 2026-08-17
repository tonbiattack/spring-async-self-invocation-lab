# デバッグ記録

## 契約

`NotificationService.submit`は呼び出し元をブロックせず、通知処理を別スレッドで実行する。Futureの結果が成功することだけでなく、実行スレッドを独立に観測する。

## バグ状態の観測

`mvn -q test`ではFutureの結果確認は成功したが、次のアサーションが失敗した。

```text
Expecting actual:
  "main"
not to be equal to:
  "main"
```

つまり、非同期処理の戻り値に見える`CompletableFuture`は完成していても、実処理は呼び出し元の`main`スレッドで実行されていた。

## 原因

`NotificationService.submit`が同じbeanの`send`を直接呼び出していた。Springのproxyベースのアドバイスはproxy経由の外部呼び出しで適用されるため、自己呼び出しでは`@Async`のインターセプトが起きない。

## 修正

`@Async`メソッドを`AsyncWorker`へ移し、`NotificationService`が別beanを呼ぶようにした。修正後は同じテストで呼び出し元とは異なるスレッド名になり、`mvn -q test`が成功した。

## 制約

この教材は自己呼び出しのproxy境界に焦点を当て、Executorの容量、キュー飽和、シャットダウン、再試行、永続的なジョブ管理は対象外である。
