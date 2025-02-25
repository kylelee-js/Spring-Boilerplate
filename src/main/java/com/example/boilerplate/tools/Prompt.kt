package com.example.boilerplate.tools

import com.example.boilerplate.openAI.dto.ChatRequestDto

enum class Emotion(val value: String) {
    DEFAULT("""
              "rotate_pitch": 0,
              "rotate_yaw": 0,
              "rotate_roll": 0,
              "blink": 0,
              "eyebrow": 0,
              "wink": 0,
              "pupil_x": 0,
              "pupil_y": 0,
              "aaa": 0,
              "eee": 0,
              "woo": 0,
              "smile": 0,
    """.trimIndent()),
    HAPPY("""
              "rotate_pitch": 0,
              "rotate_yaw": 0.6,
              "rotate_roll": 0,
              "blink": 0,
              "eyebrow": 0,
              "wink": 14.2,
              "pupil_x": 0,
              "pupil_y": 0,
              "aaa": 0,
              "eee": 0,
              "woo": 0,
              "smile": 1.3,
    """.trimIndent()),
    SAD("""
              "rotate_pitch": 0,
              "rotate_yaw": 0,
              "rotate_roll": 0,
              "blink": 0,
              "eyebrow": -10,
              "wink": 0,
              "pupil_x": -15,
              "pupil_y": -15,
              "aaa": -8,
              "eee": 3.5,
              "woo": 14.2,
              "smile": -0.3,
    """.trimIndent()),
}

class Prompt {
    companion object {
        fun scriptToJson(): ChatRequestDto.MessageDto {
            return ChatRequestDto.MessageDto("system", """
                당신은 소설을 JSON 형식의 대화 스크립트로 변환하는 AI입니다.  
                
                emotion은 6가지 종류를 따릅니다. 1) default, 2) happy, 3) sad, 4) angry, 5) surprised, 6) scared
                다음과 같은 JSON 구조를 따르세요:
{
  "scenes": [
    {
      "id": "0",
      "location": "영주의 방 문앞",
      "description": "두 눈이 충혈 된 그레이. 거대한 철문 앞에 선다.\n깊은 생각에 빠진 그레이. 철문 손잡이를 잡으며 문을 연다.",
      "narration": ["그레이는 붉게 충혈된 눈으로 거대한 문 앞에 섰다.", "이 너머에 그가 그토록 찾아 헤매던 진실이 있다.", "거짓말처럼 새카만, 차가움을 넘어 음산한 기분마저 드는 그 거대한 철문 앞에서 그레이는 생각했다."]
    },
    {
      "id": "1",
      "location": "그레이의 집 앞 (낮)",
      "description": ["크게 하품을 하는 그레이.", "자신의 정원에서 마당을 살피다 무언 갈 발견하는 그레이.", "낡은 빨간 우체통에 깃이 세워져 있다.", "얼굴이 굳어지는 그레이."]
      "dialogue": [
        {
          "speaker": "그레이",
          "emotion": "default",
          "text": "어젯밤 바람이 많이 부는 것 같더니, 별일이 다 있군."
        },
        {
          "speaker": "그레이",
          "emotion": "default",
          "text": "문제는 좋은 소식일 리가 없다는 거지만..."
        }
      ],
      "narration": ["그레이는 크게 하품하며 현관을 열었다.", "날은 밝았고, 그의 새롭고 작은 취미인 텃밭 역시 어제와 같은 푸르름을 내뿜었다."]
    }
  ]
}
            """)
        }

        fun experssion(emotion: Emotion, imageUrl: String, outputUrl: String): String{
            val propmt = """
            {
  "14": {
    "inputs": {
      ${emotion.value}
      "src_ratio": 1,
      "sample_ratio": 1,
      "sample_parts": "OnlyExpression",
      "crop_factor": 1.5,
      "src_image": [
        "15",
        0
      ]
    },
    "class_type": "ExpressionEditor",
    "_meta": {
      "title": "Expression Editor (PHM)"
    }
  },
  "15": {
    "inputs": {
      "image": "$imageUrl",
      "upload": "image"
    },
    "class_type": "LoadImage",
    "_meta": {
      "title": "Load Image"
    }
  },
  "33": {
    "inputs": {
      "filename_prefix": "ExpressionEditing",
      "images": [
        "14",
        0
      ]
    },
    "class_type": "SaveImage",
    "_meta": {
      "title": "Save Image"
    }
  }
}
            """

            return propmt
        }
    }
}