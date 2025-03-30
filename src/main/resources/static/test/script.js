document.addEventListener('DOMContentLoaded', function() {
    const questionContainer = document.getElementById('question-container');
    const submitButton = document.getElementById('submit-button');
    const resultContainer = document.getElementById('result-container');
    const resultText = document.getElementById('result-text');
    let currentQuestionIndex = 0;
    let questions = [];
    let answers = {};

    function fetchQuestion(ontoId) {
        fetch(`http://localhost:8080/`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                questions.push(data);
                if (data) {
                    renderQuestion();
                    submitButton.style.display = 'block';
                }
            })
            .catch(error => {
                console.error('Ошибка при загрузке вопроса:', error);
                questionContainer.innerHTML = '<p>Не удалось загрузить вопрос.</p>';
            });
    }

    function renderQuestion() {
        const currentQuestion = questions[currentQuestionIndex];
        if (!currentQuestion) {
            resultContainer.style.display = 'block';
            submitButton.style.display = 'none';
            resultText.textContent = 'Опрос завершен!';
            return;
        }

        questionContainer.innerHTML = '';
        const questionDiv = document.createElement('div');
        questionDiv.classList.add('text');

        const text = document.createElement('p');
        text.textContent = currentQuestion.text;
        questionDiv.appendChild(text);

        if (currentQuestion.questionType === 'radio') {
            currentQuestion.options.forEach((option, index) => {
                const radioInput = document.createElement('input');
                radioInput.type = 'radio';
                radioInput.name = `question-${currentQuestionIndex}`;
                radioInput.id = `option-${index}`;
                radioInput.value = option;

                const label = document.createElement('label');
                label.htmlFor = `option-${index}`;
                label.textContent = option;

                questionDiv.appendChild(radioInput);
                questionDiv.appendChild(label);
                questionDiv.appendChild(document.createElement('br'));

                radioInput.addEventListener('change', () => {
                  answers[currentQuestion.id] = radioInput.value
                })
            });
        } else if (currentQuestion.questionType === 'text') {
            const textInput = document.createElement('input');
            textInput.type = 'text';
            textInput.name = `question-${currentQuestionIndex}`;
            questionDiv.appendChild(textInput);

            textInput.addEventListener('input', () => {
                answers[currentQuestion.id] = textInput.value
            })
        }

        questionContainer.appendChild(questionDiv);
    }

    submitButton.addEventListener('click', function() {
        currentQuestionIndex++;
        fetchQuestion(currentQuestionIndex + 1);
    });

    fetchQuestion(1);
});