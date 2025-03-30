document.addEventListener('DOMContentLoaded', function() {
    // Инициализация частиц
    particlesJS('particles-js', {
        "particles": {
            "number": {
                "value": 80,
                "density": {
                    "enable": true,
                    "value_area": 800
                }
            },
            "color": {
                "value": ["#6e45e2", "#88d3ce", "#ff7e5f"]
            },
            "shape": {
                "type": "circle",
                "stroke": {
                    "width": 0,
                    "color": "#000000"
                }
            },
            "opacity": {
                "value": 0.3,
                "random": true,
                "anim": {
                    "enable": true,
                    "speed": 1,
                    "opacity_min": 0.1,
                    "sync": false
                }
            },
            "size": {
                "value": 3,
                "random": true,
                "anim": {
                    "enable": true,
                    "speed": 2,
                    "size_min": 0.1,
                    "sync": false
                }
            },
            "line_linked": {
                "enable": true,
                "distance": 150,
                "color": "#6e45e2",
                "opacity": 0.2,
                "width": 1
            },
            "move": {
                "enable": true,
                "speed": 1,
                "direction": "none",
                "random": true,
                "straight": false,
                "out_mode": "out",
                "bounce": false,
                "attract": {
                    "enable": true,
                    "rotateX": 600,
                    "rotateY": 1200
                }
            }
        },
        "interactivity": {
            "detect_on": "canvas",
            "events": {
                "onhover": {
                    "enable": true,
                    "mode": "grab"
                },
                "onclick": {
                    "enable": true,
                    "mode": "push"
                },
                "resize": true
            },
            "modes": {
                "grab": {
                    "distance": 140,
                    "line_linked": {
                        "opacity": 0.5
                    }
                },
                "push": {
                    "particles_nb": 4
                }
            }
        },
        "retina_detect": true
    });

    // Инициализация AOS (анимации при скролле)
    AOS.init({
        duration: 800,
        easing: 'ease-in-out',
        once: true
    });

    // Переключение темы
    const themeSwitch = document.getElementById('theme-switch');
    themeSwitch.addEventListener('change', function() {
        document.documentElement.setAttribute('data-theme', this.checked ? 'light' : 'dark');
    });

    // Обработка формы
    const form = document.getElementById('survey-form');
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        // Сбор данных
        const formData = new FormData(form);
        const data = {};
        formData.forEach((value, key) => {
            data[key] = value;
        });

        console.log('Данные опроса:', data);

        // Анимация успешной отправки
        showSuccess();
    });

    // Эффект при наведении на звезды рейтинга
    const stars = document.querySelectorAll('.rating-stars label');
    const ratingFill = document.querySelector('.rating-fill');

    stars.forEach(star => {
        star.addEventListener('mouseenter', function() {
            const index = Array.from(stars).indexOf(this);
            const width = (stars.length - index) * 20;
            ratingFill.style.width = `${width}%`;
        });

        star.addEventListener('mouseleave', function() {
            const checkedStar = document.querySelector('.rating-stars input:checked');
            if (checkedStar) {
                const index = Array.from(stars).indexOf(checkedStar.nextElementSibling);
                const width = (stars.length - index) * 20;
                ratingFill.style.width = `${width}%`;
            } else {
                ratingFill.style.width = '0%';
            }
        });
    });

    // Функция показа успешной отправки
    function showSuccess() {
        const form = document.getElementById('survey-form');
        form.innerHTML = `
            <div class="success-animation">
                <svg class="checkmark" viewBox="0 0 52 52">
                    <circle class="checkmark-circle" cx="26" cy="26" r="25" fill="none"/>
                    <path class="checkmark-check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
                </svg>
                <h3>Спасибо за ответы!</h3>
                <p>Ваше мнение очень важно для нас</p>
                <button class="reset-btn">Заполнить еще раз</button>
            </div>
        `;

        // Анимация кнопки сброса
        const resetBtn = document.querySelector('.reset-btn');
        resetBtn.addEventListener('click', function() {
            location.reload();
        });
    }
});