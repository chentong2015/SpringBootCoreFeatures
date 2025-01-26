let inputs = document.getElementsByTagName("input");

for (let i = 0; i < inputs.length; i++) {
    if (inputs[i].type === "checkbox") {
        let checkbox = inputs[i];
        checkbox.addEventListener('change', function () {
            let idValue = checkbox.value;
            AddAnimationToCart(this.checked, idValue);
            RefreshShoppingCartCount(this.checked);
            RefreshTotalPrice(this.checked, idValue);
            ResetElementSelect(this.checked, idValue);
            RefreshOrderButton();
        });
    }
}

function ResetElementSelect(isChecked, idValue) {
    let elementSelect = document.getElementById("select_" + idValue);
    if (isChecked) {
        elementSelect.disabled = false;
    } else {
        elementSelect.disabled = true;
        elementSelect.value = 1;
    }
}

function AddAnimationToCart(isChecked, idValue) {
    if (isChecked) {
        AddToCart($('#' + idValue), $('.shoppingCartImage'), function () {
            console.log('Add to shopping cart')
        })
    }
}

function AddToCart(fromEl, toEl, callback) {
    const fromX = fromEl.offset().left;
    const fromY = fromEl.offset().top - $(document).scrollTop();
    const toX = toEl.offset().left;
    const toY = toEl.offset().top - $(document).scrollTop();

    const img = document.createElement('img');
    img.src = fromEl.attr('src')
    img.style.width = fromEl.width() + 'px'
    img.style.height = fromEl.height() + 'px'
    img.style.position = 'fixed'
    img.style.zIndex = '1000'
    img.style.left = fromX + 'px'
    img.style.top = fromY + 'px'

    document.getElementsByTagName('body')[0].appendChild(img)
    const fakeEl = $(img);
    fakeEl.animate({
        width: fromEl.width() * 0.2,
        height: fromEl.height() * 0.2,
        left: toX,
        top: toY
    }, 1000, null, function () {
        fakeEl.remove()
        if (callback) {
            callback()
        }
    })
}

function RefreshShoppingCartCount(isChecked) {
    let elementSpan = document.getElementById("idCart");
    let count = elementSpan.innerText;
    if (isChecked) {
        count++;
    } else {
        count--;
    }
    let marginLeft;
    if (count > 9) {
        marginLeft = "33px";
    } else if (count > 99) {
        marginLeft = "28px";
    } else {
        marginLeft = "38px";
    }
    elementSpan.style.marginLeft = marginLeft;
    elementSpan.textContent = count;
}

function RefreshTotalPrice(isChecked, idValue) {
    let priceElement = document.getElementById(idValue + "_price");
    const basePrice = priceElement.value;
    let totalPriceSpan = document.getElementById("totalPrice");
    const totalPrice = totalPriceSpan.innerText;
    let newTotalPrice;
    if (isChecked) {
        // By default, add single one component price
        newTotalPrice = parseFloat(totalPrice) + parseFloat(basePrice);
    } else {
        let elementSelect = document.getElementById("select_" + idValue);
        let count = parseFloat(elementSelect.value)
        newTotalPrice = parseFloat(totalPrice) - parseFloat(basePrice) * count;
    }
    totalPriceSpan.textContent = newTotalPrice;
}

function RefreshOrderButton() {
    let elementSpan = document.getElementById("idCart");
    let orderButton = document.getElementById("idOrderButton");
    let count = elementSpan.innerText;
    if (count > 0) {
        orderButton.disabled = false;
    } else {
        orderButton.disabled = true;
    }
}

// Select Element event listener
let oldSelectedValue;

function SetOldValue(select) {
    oldSelectedValue = select.value;
}

function Change(select) {
    const id = select.id;
    const myArray = id.split("_");
    let idValue = myArray[1] + "_" + myArray[2];
    let count = parseInt(select.value) - parseInt(oldSelectedValue);
    oldSelectedValue = select.value
    console.log(count)

    let priceElement = document.getElementById(idValue + "_price");
    const basePrice = priceElement.value;
    let totalPriceSpan = document.getElementById("totalPrice");
    const totalPrice = totalPriceSpan.innerText;
    const addPrice = parseFloat(basePrice) * count;
    totalPriceSpan.textContent = parseFloat(totalPrice) + addPrice;
}
