import {useEffect, useState, createContext, useContext} from "react";
import {Button, Form, Modal} from 'react-bootstrap'
import axios from 'axios';
import {toast} from 'react-toastify';
import {ReportIssue} from '../ReportIssue/ReportIssue';
import {Loader} from '../Loader/Loader';
import './CardInfo.scss'

export const CardInfoContext = createContext([]);

const LockConfirmation = ({show, onCancel, onContinue}) => {
    return (
        <Modal show={show}>
            <Modal.Body className="pb-0">
                <div>Locking card will prevent the following:</div>
                <ul>
                    <li>Purchases</li>
                    <li>Cash Advances</li>
                    <li>ATM Withdraws</li>
                    <li>Balance transfers</li>
                </ul>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onCancel}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={() => onContinue()}>
                    Continue
                </Button>
            </Modal.Footer>
        </Modal>
    )
};

export const CardInfoCards = () => {
    const [cards, updateCard] = useContext(CardInfoContext);
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [currentCard, setCurrentCard] = useState();
    const handleCardToggle = (card) => async () => {
        setCurrentCard(card)
        if (!card.active) {
            await updateCardStatus(card);
        } else {
            setShowConfirmation(true)
        }
    };
    const handleConfirmationClear = () => {
        setCurrentCard(undefined);
        setShowConfirmation(false);
    };
    const updateCardStatus = async (card = currentCard) => {
        axios.put('/v1/card-control/update-status', card)
            .then(({data: updatedCard}) => {
                if (card.active !== updatedCard.active) {
                    toast.success(`Card ${updatedCard.active ? 'unlocked' : 'locked'}!`,
                        {position: toast.POSITION.BOTTOM_CENTER}
                    );
                }
                updateCard(updatedCard);
            }).catch(e => toast.error(e.message, {position: toast.POSITION.BOTTOM_CENTER}))
            .finally(handleConfirmationClear);
    };
    const cardList = (cards || []).map(card => {
        const {cardId, cardName, maskedCardNumber, active} = card;
        return (
            <div className="card-info-card" key={cardId}>
                <div className="card-info-card__details">
                    <div className="card-info-card__name fw-bold">{cardName}</div>
                    <div className="card-info-card__mask-number fw-light lh-1">
                        Card Number: {maskedCardNumber}
                    </div>
                </div>
                <div className="card-info-card__actions">
                    <Form>
                        <Form.Switch checked={active}
                                     onChange={handleCardToggle(card)}
                        />
                    </Form>
                </div>
            </div>
        );
    });
    return (
        <>
            <div className="card-info-cards">
                {cardList}
            </div>
            <LockConfirmation show={showConfirmation}
                              onCancel={handleConfirmationClear}
                              onContinue={updateCardStatus}/>
        </>
    );
};

export const CardInfo = () => {
    const [cards, setCards] = useState();
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        setLoading(true);
        axios.get('/v1/card-info/00121')
            .then(({data: {cards}}) => setCards(cards))
            .catch(e => toast.error(e.message, {position: toast.POSITION.BOTTOM_CENTER}))
            .finally(() => setLoading(false));
    }, []);

    const updateCard = (card) => {
        const updatedCards = (cards || []).map(c => {
            if (c.cardId === card.cardId) {
                return card;
            }
            return c;
        })
        setCards(updatedCards);
    };

    return (
        <CardInfoContext.Provider value={[cards, updateCard]}>
            <div className="card-info pt-3">
                <div className="card-info__header">
                    <h3 className="h3 px-2 pb-0 mb-0 mt-1">Cards:</h3>
                    <ReportIssue/>
                </div>
                <Loader loading={loading}/>
                <CardInfoCards/>
            </div>
        </CardInfoContext.Provider>
    )
};
