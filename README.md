# dkssud: QWERTY 한영 매핑 프로그램

<p align="center">
  <img width="300" src="https://github.com/user-attachments/assets/d38787e5-de1f-4124-921f-f4d365b87b94" alt="GDG"/></br>
</p>

---

QWERTY 키보드용 한국어/영어 간 매핑 라이브러리입니다.

QWERTY 키보드에서 한글을 입력하거나, 반대로 영어로 변환할 수 있습니다.

이 라이브러리는 [gksdudaovld 한영매핑](https://github.com/ForestHouse2316/gksdudaovld) Python 라이브러리에서 영감을 받아 제작되었습니다.

Go버전 라이브러리는 [dkssud-go](https://github.com/Alfex4936/dkssud)

> [!NOTE]
> dkssud 은 '안녕'을 영어로 치면 나옵니다.

## 소개

`dkssud` 패키지를 사용하면 다음과 같이 QWERTY 키보드 입력을 한글로 변환할 수 있습니다:

### Java 사용 예시

```java
import csw.dkssud.HangulMapper;

public class Main {
    public static void main(String[] args) {
        String result = HangulMapper.qwertyToHangul("dkssud");
        System.out.println(result); // 출력: "안녕"
    }
}
```

또는 한글을 영어로 변환할 수 있습니다:

```java
import csw.dkssud.HangulMapper;

public class Main {
    public static void main(String[] args) {
        String result = HangulMapper.hangulToQwerty("안녕하세요");
        System.out.println(result); // 출력: "dkssudgktpdy"
    }
}
```

## 설치

### Maven

```xml
<!--저장소 추가-->
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<!--종속 추가-->
<dependency>
    <groupId>com.github.Alfex4936</groupId>
    <artifactId>dkssud-java</artifactId>
    <version>v1.0.1</version>
</dependency>
```

## 사용 예시

### QWERTY -> 한글 변환 (Java)

QWERTY 키보드 입력을 한글로 변환하는 간단한 예제입니다:

```java
import csw.dkssud.HangulMapper;

public class Main {
    public static void main(String[] args) {
        String hangul = HangulMapper.qwertyToHangul("rkskekfk");
        System.out.println(hangul); // 출력: "가나다라"

        hangul = HangulMapper.qwertyToHangul("rjRlRkwldii");
        System.out.println(hangul); // 출력: "거끼까지야ㅑ"
    }
}
```

### 한글 -> QWERTY 변환 (Java)

한글을 QWERTY 키보드 입력으로 변환하는 예제입니다:

```java
import csw.dkssud.HangulMapper;

public class Main {
    public static void main(String[] args) {
        String qwerty = HangulMapper.hangulToQwerty("안녕하세요");
        System.out.println(qwerty); // 출력: "dkssudgktpdy"

        qwerty = HangulMapper.hangulToQwerty("뮻ㅇ");
        System.out.println(qwerty); // 출력: "abcd"
    }
}
```

### 유틸리티 (Java)

```java
import csw.dkssud.HangulMapper;

public class Main {
    public static void main(String[] args) {
        // QWERTY 한글인지 간단한 확인
        boolean isQwerty = HangulMapper.isQwertyHangul("안녕하세요"); // false - 한글이 포함되어 있으므로 QWERTY 한글이 아님
        System.out.println(isQwerty);

        isQwerty = HangulMapper.isQwertyHangul("dkssudgktpdy"); // true - QWERTY로 입력된 한글로 인식됨
        System.out.println(isQwerty);

        // 두 문자가 한글 결합 가능한지 확인
        int isAttachable = HangulMapper.isAttachAvailable('r', 'k'); // 2 - 'r'과 'k'는 자음과 모음으로 결합 가능
        System.out.println(isAttachable);
    }
}
```