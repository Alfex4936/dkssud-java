# dkssud: QWERTY 한영 매핑 프로그램

<p align="center">
  <img width="300" src="https://github.com/user-attachments/assets/95f3369b-cdd2-4297-9ec4-e9ad48799ff5" alt="GDG"/></br>
</p>

---

[![GoDoc](https://pkg.go.dev/badge/github.com/Alfex4936/dkssud.svg)](https://pkg.go.dev/github.com//Alfex4936/dkssud)
[![codecov](https://codecov.io/gh/Alfex4936/dkssud/graph/badge.svg?token=PYJT7QQ4BW)](https://codecov.io/gh/Alfex4936/dkssud)

QWERTY 키보드용 한국어/영어 간 매핑 라이브러리입니다.

QWERTY 키보드에서 한글을 입력하거나, 반대로 영어로 변환할 수 있습니다.

이 라이브러리는 [gksdudaovld 한영매핑](https://github.com/ForestHouse2316/gksdudaovld) Python 라이브러리에서 영감을 받아 제작되었습니다.


> [!NOTE]
> dkssud 은 '안녕'을 영어로 치면 나옵니다.

## 소개

`dkssud` 패키지를 사용하면 다음과 같이 QWERTY 키보드 입력을 한글로 변환할 수 있습니다:

```go
import "github.com/Alfex4936/dkssud"

func main() {
    result := dkssud.QwertyToHangul("dkssud")
    fmt.Println(result) // 출력: "안녕"
}
```

위와 같이 한국어를 영어로, 또는 영어를 한국어로 바꿀 수 있습니다.

## 설치

```bash
go get github.com/Alfex4936/dkssud
```

## 사용 예시

### QWERTY -> 한글 변환

QWERTY 키보드 입력을 한글로 변환하는 간단한 예제입니다:

```go
import "github.com/Alfex4936/dkssud"

func main() {
    hangul := dkssud.QwertyToHangul("rkskekfk")
    fmt.Println(hangul) // 출력: "가나다라"

    // 또는 (QwertyToHangul 랑 같음)
    hangul = dkssud.쿼티("rkskekfk")
    fmt.Println(hangul) // 출력: "가나다라"

    hangul = dkssud.QwertyToHangul("rjRlRkwldii")
    fmt.Println(hangul) // 출력: "거끼까지야ㅑ"
}
```

### 한글 -> QWERTY 변환

한글을 QWERTY 키보드 입력으로 변환하는 예제입니다:

```go
import "github.com/Alfex4936/dkssud"

func main() {
    qwerty := dkssud.HangulToQwerty("안녕하세요")
    fmt.Println(qwerty) // 출력: "dkssudgktpdy"

    // 또는 (HangulToQwerty 랑 같음)
    qwerty = dkssud.한글("안녕하세요")
    fmt.Println(hangul) // 출력: "dkssudgktpdy"

    qwerty = dkssud.HangulToQwerty("뮻ㅇ")
    fmt.Println(qwerty) // 출력: "abcd"
}
```

### 유틸리티
```go
import "github.com/Alfex4936/dkssud"

func main() {
    // QWERTY 한글인지 간단한 확인
    isQwerty := dkssud.IsQwertyHangul("안녕하세요") // false - 한글이 포함되어 있으므로 QWERTY 한글이 아님
    isQwerty = dkssud.IsQwertyHangul("dkssudgktpdy") // true - QWERTY로 입력된 한글로 인식됨
    isQwerty = dkssud.IsQwertyHangul("dks녕gktpdy") // false - 한글이 포함되어 있으므로 QWERTY 한글이 아님
    isQwerty = dkssud.IsQwertyHangul("hello there") // true - 알고리즘 한계, 영단어면 true

    // 두 문자가 한글 결합 가능한지 확인
    isAttachable := dkssud.IsAttachAvailable('r', 'k') // 2 - 'r'과 'k'는 자음과 모음으로 결합 가능
    isAttachable = dkssud.IsAttachAvailable('k', 'o') // 0 - 모 + 모, 결합 불가
    isAttachable = dkssud.IsAttachAvailable('k', 'r') // 4 - 모 + 자
}
```
